package palbp.laboratory.solutions.en2425

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import palbp.laboratory.demos.coroutines.exercises.PeriodicMessage
import kotlin.time.Duration


/**
 * [4] Consider the following definition, which represents a message that must be cyclically displayed at
 * delayBetweenPresentations intervals.
 *
 * <pre> {@code
 * class PeriodicMessage(val message: String, val delayBetweenPresentations: Duration)
 * }</pre>
 *
 * Implement the following non-suspend function.
 *
 * <pre> {@code
 * fun show(showString: (String) -> Unit, totalDuration: Duration, vararg periodicMessages: PeriodicMessage)
 * }</pre>
 *
 * The show function displays the periodicMessages for a maximum of totalDuration, after which it must return.
 * The showString function must be used to display each string. Assume that showString is not thread-safe and
 * cannot be called simultaneously from multiple threads. The implemented show function must be thread-safe.
 * Unlike the second practical assignment, the solution must use features from the kotlinx.coroutines package.
 *
 * Script:
 * 1 - Remember to declare the mutex outside the show function so all calls to show can use the same mutex.
 * 2 - Use the launch function to create a coroutine for each periodic message.
 * 3 - Use two approaches:
 *  3.1 - a parent job which is cancelled once the total duration is reached (delay based approach). Child jobs
 *  cycle until the parent job is cancelled.
 *  3.2 - no parent job and no cancellation. Each child job cycles until the total duration is reached (time check
 *  based approach).
 */
data class PeriodicMessage(val message: String, val delayBetweenPresentations: Duration)

private val guard = Mutex()

fun show(showString: (String) -> Unit, totalDuration: Duration, vararg periodicMessages: PeriodicMessage) =
    runBlocking {
        val outerJob = launch {
            periodicMessages.forEach {
                launch {
                    while (true) {
                        guard.withLock { showString(it.message) }
                        delay(it.delayBetweenPresentations)
                    }
                }
            }
        }

        delay(totalDuration)
        outerJob.cancel()
    }

