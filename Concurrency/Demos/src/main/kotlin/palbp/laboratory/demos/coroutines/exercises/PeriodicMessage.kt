package palbp.laboratory.demos.coroutines.exercises

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant.now
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Represents a periodic message that can be displayed.
 * @param message the message to be displayed
 * @param delayBetweenPresentations the delay between consecutive presentations of the message
 */
data class PeriodicMessage(val message: String, val delayBetweenPresentations: Duration)

/**
 * Shows a series of periodic messages until the total duration is reached.
 * @param showString a function that takes a string and displays it
 * @param totalDuration the total duration for which the messages should be shown
 * @param periodicMessages the messages to be displayed periodically
 */
private val showStringGuard = Mutex()
fun show(showString: (String) -> Unit, totalDuration: Duration, vararg periodicMessages: PeriodicMessage) = runBlocking {
    launch {
        val endTime = now().plusMillis(totalDuration.inWholeMilliseconds)
        periodicMessages.forEach { periodicMessage ->
            launch {
                while (now() < endTime) {
                    showStringGuard.withLock { showString(periodicMessage.message) }
                    delay(duration = periodicMessage.delayBetweenPresentations)
                }
            }
        }
    }
}

fun main() {
    show(
        showString = { println(it) },
        totalDuration = 5.toDuration(DurationUnit.SECONDS),
        PeriodicMessage("Hello, World!", 1.toDuration(DurationUnit.SECONDS)),
        PeriodicMessage("Kotlin Coroutines are fun!", 2.toDuration(DurationUnit.SECONDS))
    )
}