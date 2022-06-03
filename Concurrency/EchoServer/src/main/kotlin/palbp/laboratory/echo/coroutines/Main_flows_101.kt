package palbp.laboratory.echo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import palbp.laboratory.echo.AsyncSemaphore
import java.net.Socket
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

private const val EXIT = "exit"
private val logger = LoggerFactory.getLogger("Coroutine and NIO2 based Echo Server")

/**
 * Number of client sessions initiated during the server's current execution
 */
private val sessionCount = AtomicInteger(0)

/**
 * Creates a client session, incrementing the number of initiated sessions.
 */
private fun createSession(): Int = sessionCount.incrementAndGet()

/**
 * The server's entry point.
 */
fun main(args: Array<String>) {

    val port = if (args.isEmpty() || args[0].toIntOrNull() == null) 8000 else args[0].toInt()
    logger.info("Process id is = ${ProcessHandle.current().pid()}. Starting echo server at port $port")

    val executor = Executors.newSingleThreadExecutor()
    val serverSocket = createServerChannel("localhost", port, executor)

    val scope = CoroutineScope(executor.asCoroutineDispatcher())
    val throttle = AsyncSemaphore(2)
    scope.launch {
        serverSocket.toFlow(throttle).collect { sessionSocket ->
            logger.info("Connection accepted")
            launch {
                try {
                    handleEchoSession(sessionSocket)
                }
                finally {
                    throttle.release()
                }
            }
        }
        logger.info("Parent coroutine ends")
    }

    readln()

    logger.info("Shutting down...")
    scope.cancel()
    executor.shutdown()
    val terminated = executor.awaitTermination(5, TimeUnit.SECONDS)
    logger.info("Done. Executor terminated successfully? $terminated")
}

/**
 * Serves the client connected to the given [Socket] instance
 */
private suspend fun handleEchoSession(sessionSocket: AsynchronousSocketChannel) {
    val sessionId = createSession()
    var echoCount = 0

    sessionSocket.use {
        logger.info("Accepted session $sessionId")
        it.suspendingWriteLine("Welcome client number $sessionId!")
        it.suspendingWriteLine("I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!")

        it.toFlow().collect { line ->
            logger.info("Received line number '${++echoCount}'. Echoing it.")
            it.suspendingWriteLine("($echoCount) Echo: $line")
        }

        it.suspendingWriteLine("Bye!")
        logger.info("Session $sessionId ends")
    }
}

/**
 * Extension function that produces a flow of [AsynchronousSocketChannel] client connections from this
 * [AsynchronousServerSocketChannel] instance.
 */
suspend fun AsynchronousServerSocketChannel.toFlow(throttle: AsyncSemaphore): Flow<AsynchronousSocketChannel> =
    flow {
        try {
            while(true) {
                throttle.acquire().await()
                logger.info("Ready to accept connections")
                val sessionSocket = suspendingAccept()
                emit(sessionSocket)
            }
        }
        finally {
            logger.info("Flow of connections ends")
        }
    }

/**
 * Extension function that produces a flow of client messages (text lines) received from this
 * [AsynchronousSocketChannel] instance.
 */
suspend fun AsynchronousSocketChannel.toFlow(): Flow<String> =
    flow {
        while (true) {
            val line = suspendingReadLine()
            if (line == null || line == EXIT)
                break
            emit(line)
        }
    }
