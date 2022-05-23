package palbp.laboratory.echo

import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.channels.AsynchronousChannelGroup
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.util.LinkedList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

private const val EXIT = "exit"
private val logger = LoggerFactory.getLogger("Async callback based NIO2 Echo Server")

private val encoder: CharsetEncoder = Charsets.UTF_8.newEncoder()
private val decoder: CharsetDecoder = Charsets.UTF_8.newDecoder()

class AsyncSemaphore(initialUnits: Int) {

    private var units = initialUnits
    private val guard = ReentrantLock()
    private val queue = LinkedList<Request>()

    private class Request : CompletableFuture<Unit>()

    fun acquire(): CompletableFuture<Unit> {
        guard.withLock {
            if (units != 0) {
                units -= 1
                return CompletableFuture.completedFuture(Unit)
            }

            val request = Request()
            queue.addLast(request)
            return request
        }
    }

    fun release() {
        guard.withLock {
            if (queue.isEmpty()) {
                units += 1
                null
            }
            else {
                queue.removeFirst()
            }
        }?.complete(Unit)
    }
}

private const val MAX_SESSIONS: Int = 2

/**
 * The server's entry point.
 */
fun main(args: Array<String>) {

    val port = if (args.isEmpty() || args[0].toIntOrNull() == null) 8000 else args[0].toInt()

    val singleThreadedGroup = AsynchronousChannelGroup.withThreadPool(Executors.newSingleThreadExecutor())
    val serverSocket = AsynchronousServerSocketChannel.open(singleThreadedGroup)
    // val serverSocket = AsynchronousServerSocketChannel.open()
    serverSocket.bind(InetSocketAddress("localhost", port))

    logger.info("Process id is = ${ProcessHandle.current().pid()}. Starting echo server at port $port")
    val throttle = AsyncSemaphore(MAX_SESSIONS)

    fun acceptConnection() {

        throttle.acquire()
            .thenRun {
                logger.info("Ready to accept connections")
                serverSocket.accept(null, object : CompletionHandler<AsynchronousSocketChannel, Any?> {
                    override fun completed(sessionSocket: AsynchronousSocketChannel, attachment: Any?) {
                        handleEchoSession(sessionSocket, throttle)
                        acceptConnection()
                    }

                    override fun failed(exc: Throwable?, attachment: Any?) {
                        logger.error("Failed to accept.")
                    }
                })
            }
    }

    acceptConnection()
    readln()
}

/**
 * Serves the client connected to the given [Socket] instance
 */
private fun handleEchoSession(sessionSocket: AsynchronousSocketChannel, throttle: AsyncSemaphore) {
    val sessionId = SessionInfo.createSession()
    logger.info("Accepted client connection. Number of open sessions is ${SessionInfo.currentSessions}")

    greet(sessionSocket, sessionId, throttle) {
        handleEchoes(sessionSocket, throttle) {
            sayGoodbye(sessionSocket, throttle)
        }
    }
}

/**
 * Starts the echo session by greeting the client. The implementation is non-blocking.
 *
 * @param   sessionSocket   the socket connected to the client
 * @param   sessionId       the session identifier
 * @param   andThen         the continuation code, to be executed after the greeting is sent
 */
private fun greet(sessionSocket: AsynchronousSocketChannel, sessionId: Int, throttle: AsyncSemaphore, andThen: (AsynchronousSocketChannel) -> Unit) {

    val greet = CharBuffer.wrap("Welcome client number $sessionId!\n" +
            "I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!\n")

    sessionSocket.write(encoder.encode(greet), null, object : CompletionHandler<Int, Any?> {
        override fun completed(result: Int, attachment: Any?) {
            logger.info("Sent greeting to client.")
            andThen(sessionSocket)
        }
        override fun failed(exc: Throwable, attachment: Any?) {
            logger.error("Could not send greeting to client. Terminating.", exc)
            cleanup(sessionSocket, throttle)
        }
    })
}

/**
 * Echoes all received messages. The implementation is non-blocking.
 *
 * @param   sessionSocket   the socket connected to the client
 * @param   andThen         the continuation code, to be executed after when the 'exit' message is received.
 */
private fun handleEchoes(sessionSocket: AsynchronousSocketChannel, throttle: AsyncSemaphore, andThen: (AsynchronousSocketChannel) -> Unit) {
    val buffer = ByteBuffer.allocate(1024)
    handleEchoesInternal(sessionSocket, buffer, 1, throttle, andThen)
}

/**
 * Implementation of the actual echo handling logic.
 *
 * @param   sessionSocket   the socket connected to the client
 * @param   buffer          the buffer used to store received messages
 * @param   echoCount       the number of echoes produced in the current session
 * @param   andThen         the continuation code, to be executed after when the 'exit' message is received.
 */
private fun handleEchoesInternal(sessionSocket: AsynchronousSocketChannel, buffer: ByteBuffer, echoCount: Int, throttle: AsyncSemaphore, andThen: (AsynchronousSocketChannel) -> Unit) {
    sessionSocket.read(buffer, null, object : CompletionHandler<Int, Any?> {
        override fun completed(result: Int, attachment: Any?) {
            val message = decoder.decode(buffer.flip()).toString().trim()
            logger.info("Read $result bytes. Message is $message")
            if (message == EXIT) {
                andThen(sessionSocket)
            }
            else {
                logger.info("Received line number '$echoCount'. Echoing it.")
                val echo = CharBuffer.wrap("($echoCount) Echo: $message\n")
                sessionSocket.write(encoder.encode(echo), null, object : CompletionHandler<Int, Any?> {
                    override fun completed(result: Int, attachment: Any?) {
                        buffer.clear()
                        handleEchoesInternal(sessionSocket, buffer, echoCount + 1, throttle, andThen)
                    }

                    override fun failed(exc: Throwable, attachment: Any?) {
                        logger.error("Could not send echo to the client. Terminating.", exc)
                        cleanup(sessionSocket, throttle)
                    }
                })
            }
        }

        override fun failed(exc: Throwable, attachment: Any?) {
            logger.error("Could not receive message from client. Terminating.", exc)
            cleanup(sessionSocket, throttle)
        }
    })
}

/**
 * Ends the echo session.
 * @param   sessionSocket   the socket connected to the client
 */
private fun sayGoodbye(sessionSocket: AsynchronousSocketChannel, throttle: AsyncSemaphore,) {
    sessionSocket.write(encoder.encode(CharBuffer.wrap("Bye!\n")), null, object : CompletionHandler<Int, Any?> {
        override fun completed(result: Int?, attachment: Any?) {
            cleanup(sessionSocket, throttle)
        }

        override fun failed(exc: Throwable?, attachment: Any?) {
            cleanup(sessionSocket, throttle)
        }
    })
}

/**
 * Ends the echo session.
 * @param   sessionSocket   the socket connected to the client
 */
private fun cleanup(sessionSocket: AsynchronousSocketChannel, throttle: AsyncSemaphore) {
    SessionInfo.endSession()
    sessionSocket.close()
    throttle.release()
}
