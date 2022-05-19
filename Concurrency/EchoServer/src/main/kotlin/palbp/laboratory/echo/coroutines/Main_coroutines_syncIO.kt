package palbp.laboratory.echo.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import palbp.laboratory.echo.println
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
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
private fun createSession(): Int = sessionCount++

/**
 * The server's entry point.
 */
fun main() {
    val port = 8000
    val serverSocket = ServerSocket(port)
    logger.info("Starting echo server at port $port")

    runBlocking {
        while (true) {
            logger.info("Ready to accept connections")
            val sessionSocket = serverSocket.suspendingAccept()
            logger.info("Accepted client connection. Remote host is ${sessionSocket.inetAddress}")
            launch {
                handleEchoSession(sessionSocket)
            }
        }
    }
}

/**
 * Serves the client connected to the given [Socket] instance
 */
private suspend fun handleEchoSession(sessionSocket: Socket) {
    val sessionId = createSession()
    var echoCount = 0
    sessionSocket.use {
        val input = BufferedReader(InputStreamReader(sessionSocket.suspendingGetInputStream()))
        val output = BufferedWriter(OutputStreamWriter(sessionSocket.suspendingGetOutputStream()))
        output.suspendingPrintln("Welcome client number $sessionId!")
        output.suspendingPrintln("I'll echo everything you send me. Finish with '$EXIT'. Ready when you are!")
        while (true) {
            val line = input.suspendingReadLine() ?: EXIT
            if (line == EXIT)
                break
            logger.info("Received line number '${++echoCount}'. Echoing it.")
            output.suspendingPrintln("($echoCount) Echo: $line")
        }
        output.suspendingPrintln("Bye!")
    }
}

private suspend fun Socket.suspendingGetInputStream(): InputStream = withContext(Dispatchers.IO) { getInputStream() }
private suspend fun Socket.suspendingGetOutputStream(): OutputStream = withContext(Dispatchers.IO) { getOutputStream() }
private suspend fun ServerSocket.suspendingAccept(): Socket = withContext(Dispatchers.IO) { accept() }
private suspend fun BufferedReader.suspendingReadLine(): String = withContext(Dispatchers.IO) { readLine() }
private suspend fun BufferedWriter.suspendingPrintln(text: String) = withContext(Dispatchers.IO) { println(text) }
