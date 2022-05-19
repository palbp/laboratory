package palbp.laboratory.echo.coroutines

import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.channels.AsynchronousChannelGroup
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.util.concurrent.ExecutorService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private val logger = LoggerFactory.getLogger("NIO extensions")
private val encoder: CharsetEncoder = Charsets.UTF_8.newEncoder()
private val decoder: CharsetDecoder = Charsets.UTF_8.newDecoder()


fun createServerChannel(hostname: String, port: Int, executor: ExecutorService): AsynchronousServerSocketChannel {
    val group = AsynchronousChannelGroup.withThreadPool(executor)
    val serverSocket = AsynchronousServerSocketChannel.open(group)
    serverSocket.bind(InetSocketAddress(hostname, port))
    return serverSocket
}

suspend fun AsynchronousServerSocketChannel.suspendingAccept(): AsynchronousSocketChannel {
    return suspendCoroutine { continuation ->
        accept(null, object : CompletionHandler<AsynchronousSocketChannel, Any?> {
            override fun completed(socketChannel: AsynchronousSocketChannel, attachment: Any?) {
                //logger.info("Accept completed. Cancelled = ${continuation.isCancelled}")
                continuation.resume(socketChannel)
            }

            override fun failed(error: Throwable, attachment: Any?) {
                //logger.info("Accept failed. Cancelled = ${continuation.isCancelled}")
                continuation.resumeWithException(error)
            }
        })
    }
}


suspend fun AsynchronousSocketChannel.suspendingWriteLine(text: String): Int {
    return suspendCoroutine { continuation ->
        val toSend = CharBuffer.wrap(text + "\n")
        // This is NOT production ready! It's a DEMO!
        // E.g. We would need to deal with the case when not all the string's chars are written in one call
        write(encoder.encode(toSend), null, object : CompletionHandler<Int, Any?> {
            override fun completed(result: Int, attachment: Any?) {
                //logger.info("Write succeeded. Cancelled = ${continuation.isCancelled}")
                continuation.resume(result)
            }

            override fun failed(error: Throwable, attachment: Any?) {
                //logger.error("Write failed. Cancelled = ${continuation.isCancelled}")
                continuation.resumeWithException(error)
            }
        })
    }
}

suspend fun AsynchronousSocketChannel.suspendingReadLine(): String {
    return suspendCoroutine { continuation ->
        val buffer = ByteBuffer.allocate(1024)
        // This is NOT production ready! It's a DEMO!
        // E.g. We would need to deal with the case when read does not contain the whole line
        read(buffer, null, object : CompletionHandler<Int, Any?> {
            override fun completed(result: Int, attachment: Any?) {
                //logger.info("Read succeeded. Cancelled = ${continuation.isCancelled}")
                val received = decoder.decode(buffer.flip()).toString().trim()
                continuation.resume(received)
            }

            override fun failed(error: Throwable, attachment: Any?) {
                //logger.error("Read failed. Cancelled = ${continuation.isCancelled}")
                continuation.resumeWithException(error)
            }
        })
    }
}