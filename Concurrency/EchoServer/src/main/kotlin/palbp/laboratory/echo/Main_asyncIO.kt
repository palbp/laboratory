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
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

private const val EXIT = "exit"
private val logger = LoggerFactory.getLogger("Async callback based NIO2 Echo Server")

private val encoder: CharsetEncoder = Charsets.UTF_8.newEncoder()
private val decoder: CharsetDecoder = Charsets.UTF_8.newDecoder()

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

    fun acceptConnection() {
        logger.info("Ready to accept connections")
        serverSocket.accept(null, object : CompletionHandler<AsynchronousSocketChannel, Any?> {
            override fun completed(sessionSocket: AsynchronousSocketChannel, attachment: Any?) {
                handleEchoSession(sessionSocket)
                acceptConnection()
            }

            override fun failed(exc: Throwable?, attachment: Any?) {
                logger.error("Failed to accept.")
            }
        })
    }

    acceptConnection()
    readln()
}

/**
 * Serves the client connected to the given [Socket] instance
 */
private fun handleEchoSession(sessionSocket: AsynchronousSocketChannel) {
    val sessionId = SessionInfo.createSession()
    logger.info("Accepted client connection. Number of open sessions is ${SessionInfo.currentSessions}")

    greet(sessionSocket, sessionId) {
        handleEchoes(sessionSocket) {
            sayGoodbye(sessionSocket)
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
private fun greet(sessionSocket: AsynchronousSocketChannel, sessionId: Int, andThen: (AsynchronousSocketChannel) -> Unit) {

    val greet = CharBuffer.wrap("Welcome client number $sessionId!\n" +
            "I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!\n")

    sessionSocket.write(encoder.encode(greet), null, object : CompletionHandler<Int, Any?> {
        override fun completed(result: Int, attachment: Any?) {
            logger.info("Sent greeting to client.")
            andThen(sessionSocket)
        }
        override fun failed(exc: Throwable, attachment: Any?) {
            logger.error("Could not send greeting to client. Terminating.", exc)
            cleanup(sessionSocket)
        }
    })
}

/**
 * Echoes all received messages. The implementation is non-blocking.
 *
 * @param   sessionSocket   the socket connected to the client
 * @param   andThen         the continuation code, to be executed after when the 'exit' message is received.
 */
private fun handleEchoes(sessionSocket: AsynchronousSocketChannel, andThen: (AsynchronousSocketChannel) -> Unit) {
    val buffer = ByteBuffer.allocate(1024)
    handleEchoesInternal(sessionSocket, buffer, 1, andThen)
}

/**
 * Implementation of the actual echo handling logic.
 *
 * @param   sessionSocket   the socket connected to the client
 * @param   buffer          the buffer used to store received messages
 * @param   echoCount       the number of echoes produced in the current session
 * @param   andThen         the continuation code, to be executed after when the 'exit' message is received.
 */
private fun handleEchoesInternal(sessionSocket: AsynchronousSocketChannel, buffer: ByteBuffer, echoCount: Int, andThen: (AsynchronousSocketChannel) -> Unit) {
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
                        handleEchoesInternal(sessionSocket, buffer, echoCount + 1, andThen)
                    }

                    override fun failed(exc: Throwable, attachment: Any?) {
                        logger.error("Could not send echo to the client. Terminating.", exc)
                        cleanup(sessionSocket)
                    }
                })
            }
        }

        override fun failed(exc: Throwable, attachment: Any?) {
            logger.error("Could not receive message from client. Terminating.", exc)
            cleanup(sessionSocket)
        }
    })
}

/**
 * Ends the echo session.
 * @param   sessionSocket   the socket connected to the client
 */
private fun sayGoodbye(sessionSocket: AsynchronousSocketChannel) {
    sessionSocket.write(encoder.encode(CharBuffer.wrap("Bye!")), null, object : CompletionHandler<Int, Any?> {
        override fun completed(result: Int?, attachment: Any?) {
            cleanup(sessionSocket)
        }

        override fun failed(exc: Throwable?, attachment: Any?) {
            cleanup(sessionSocket)
        }
    })
}

/**
 * Ends the echo session.
 * @param   sessionSocket   the socket connected to the client
 */
private fun cleanup(sessionSocket: AsynchronousSocketChannel) {
    SessionInfo.endSession()
    sessionSocket.close()
}
