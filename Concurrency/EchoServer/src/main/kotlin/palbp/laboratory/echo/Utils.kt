package palbp.laboratory.echo

import java.io.BufferedWriter
import java.util.concurrent.atomic.AtomicInteger

/**
 * Extension function that prints a new line with the given string to this [BufferedWriter].
 */
fun BufferedWriter.println(str: String) {
    write(str)
    newLine()
    flush()
}

object SessionInfo {

    private val sessionCount = AtomicInteger(0)
    private val currentSessionCount = AtomicInteger(0)

    val currentSessions: Int
        get() = currentSessionCount.get()

    val totalSessions: Int
        get() = sessionCount.get()

    fun createSession(): Int {
        currentSessionCount.incrementAndGet()
        return sessionCount.incrementAndGet()
    }

    fun endSession() {
        currentSessionCount.decrementAndGet()
    }
}
