package palbp.laboratory.echo.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import palbp.laboratory.echo.AsyncSemaphore
import java.net.Socket
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    val throttle = AsyncSemaphore(2)

    val serverLoopJob = CoroutineScope(executor.asCoroutineDispatcher()).launch {
        while(true) {
            throttle.acquire().await()
            logger.info("Ready to accept connections")
            val sessionSocket = serverSocket.suspendingAccept()
            launch {
                try { handleEchoSession(sessionSocket) }
                finally { throttle.release() }
            }
        }
    }

    // This emulates the server console loop
    readln()

    logger.info("Initiating shutting down...")
    runBlocking {
        serverLoopJob.cancelAndJoin()
    }

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
        try {
            logger.info("Accepted session $sessionId")
            it.suspendingWriteLine("Welcome client number $sessionId!")
            it.suspendingWriteLine("I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!")

            while (true) {
                when (val line = it.suspendingReadLine(5, TimeUnit.MINUTES)) {
                    EXIT -> {
                        it.suspendingWriteLine("Bye!")
                        break
                    }
                    null -> {
                        it.suspendingWriteLine("Session has been idle for too long. Terminating.")
                        break
                    }
                    else -> {
                        logger.info("Received line number '${++echoCount}'. Echoing it.")
                        it.suspendingWriteLine("($echoCount) Echo: $line")
                    }
                }
            }
        }
        catch (cancelled: CancellationException) {
            logger.error("Session cancelled", cancelled)
            withContext(NonCancellable) {
                it.suspendingWriteLine("Server is shutting down. Service will be resumed later.")
            }
        }
        finally {
            logger.info("Session $sessionId ends")
        }
    }
}

suspend fun <T> CompletableFuture<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        whenComplete { result, error ->
            if (error != null)
                continuation.resumeWithException(error)
            else
                continuation.resume(result)
        }
    }
}
