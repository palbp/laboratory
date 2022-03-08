package palbp.laboratory.echo

import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

private const val EXIT = "exit"
private val logger = LoggerFactory.getLogger("SingleThreaded Echo Server")

/**
 * Number of client sessions initiated during the server's current execution
 */
private var sessionCount = 1

/**
 * Creates a client session, incrementing the number of initiated sessions.
 */
fun createSession(): Int = sessionCount++

/**
 * The server's entry point.
 */
fun main() {
    val port = 8000
    val serverSocket = ServerSocket(port)
    logger.info("Starting echo server at port $port")
    while (true) {
        logger.info("Ready to accept connections")
        val sessionSocket = serverSocket.accept()
        logger.info("Accepted client connection. Remote host is ${sessionSocket.inetAddress}")
        handleEchoSession(sessionSocket)
    }
}

/**
 * Serves the client connected to the given [Socket] instance
 */
fun handleEchoSession(sessionSocket: Socket) {
    val sessionId = createSession()
    var echoCount = 0
    sessionSocket.use {
        val input = BufferedReader(InputStreamReader(sessionSocket.getInputStream()))
        val output = BufferedWriter(OutputStreamWriter(sessionSocket.getOutputStream()))
        output.println("Welcome client number $sessionId!")
        output.println("I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!")
        while (true) {
            val line = input.readLine() ?: EXIT
            if (line == EXIT)
                break
            logger.info("Received line number '${++echoCount}'. Echoing it.")
            output.println("Echo: $line")
        }
        output.println("Bye!")
    }
}
