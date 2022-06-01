package palbp.laboratory.echo.solution

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousCloseException
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.ClosedChannelException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


private val logger = LoggerFactory.getLogger(Server::class.java)

/**
 * Represents echo server instance. Instances accept at most [maxSessions] and can only be started once.
 * The server instance has ownership over its dependencies (i.e. [sessionManager] and [executor]), that is, it's
 * responsible for releasing all the associated resources upon shutdown.
 *
 * @property maxSessions    the maximum number of simultaneous echo sessions
 * @property sessionManager the instance used to start and end echo sessions, that is, manage their lifetime
 * @property executor       the instance used to schedule the execution of work
 */
class Server(
    private val maxSessions: Int,
    private val sessionManager: SessionManager = SessionManager(),
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
) {

    enum class State { NOT_STARTED, STARTED, STOPPED }

    private var state = State.NOT_STARTED
    private val guard = Mutex()
    private val throttle = AsyncSemaphore(maxSessions)
    private lateinit var serverLoopJob: Job
    private lateinit var serverSocketChannel: AsynchronousServerSocketChannel

    suspend fun isStarted() = guard.withLock { state == State.STARTED }

    /**
     * Starts the server, preparing it to accept requests for echo sessions.
     * @param address   the address on which the server will be listening
     * @throws  IllegalStateException if the server has already been started
     */
    suspend fun start(address: InetSocketAddress) {

        guard.withLock {
            if (state != State.NOT_STARTED)
                throw IllegalStateException("Server has already been started")

            serverSocketChannel = createServerChannel(address, executor)
            serverLoopJob = CoroutineScope(executor.asCoroutineDispatcher()).launch {
                try {
                    while(isStarted()) {
                        throttle.acquire().await()
                        logger.info("Ready to accept connections")
                        val sessionSocket = serverSocketChannel.suspendingAccept()
                        if (!isStarted()) {
                            throttle.release()
                            break
                        }

                        sessionManager.createSession(sessionSocket, this)
                            .start()
                            .onStop {
                                sessionManager.removeSession(it.id)
                                throttle.release()
                            }
                    }
                }
                catch (ex: ClosedChannelException) {
                    logger.info("Server is shutting down")
                }
            }

            state = State.STARTED
        }
    }

    /**
     * Shuts down the server and synchronizes with the termination of the shutdown sequence
     * @param message   the message to send to all connected clients
     * @throws  IllegalStateException if the server is not started
     */
    suspend fun shutdownAndJoin(message: String) {
        guard.withLock {
            if (state != State.STARTED)
                throw IllegalStateException("Server is not started")

            state = State.STOPPED
        }

        // TODO: close can actually throw an exception. Must deal with it
        serverSocketChannel.close()
        sessionManager.roaster.forEach { it.stop(message) }
        serverLoopJob.join()

        executor.shutdown()
    }
}