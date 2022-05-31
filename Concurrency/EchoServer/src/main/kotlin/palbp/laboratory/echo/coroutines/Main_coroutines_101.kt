package palbp.laboratory.echo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.net.Socket
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
    scope.launch {
        try {
            while(true) {
                logger.info("Ready to accept connections")
                val sessionSocket = serverSocket.suspendingAccept()
                logger.info("Connection accepted")
                launch {
                    try {
                        handleEchoSession(sessionSocket)
                    }
                    finally {
                        logger.info("Coroutine ends")
                    }
                }
            }
        }
        finally {
            logger.info("Parent coroutine ends")
        }
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

        while (true) {
            val line = it.suspendingReadLine()
            if (line == EXIT)
                break
            logger.info("Received line number '${++echoCount}'. Echoing it.")
            it.suspendingWriteLine("($echoCount) Echo: $line")
        }

        it.suspendingWriteLine("Bye!")
        logger.info("Session $sessionId ends")
    }
}
