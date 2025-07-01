package palbp.laboratory.demos.coroutines.exercises

import kotlinx.coroutines.sync.Mutex
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A cyclic countdown latch that allows coroutines to wait until a certain count is reached,
 * and then resets the count to allow for repeated use.
 * @param initialCount the initial count for the latch; must be greater than 0.
 */
class SuspendingCyclicCountDownLatch(val initialCount: Int) {
    init {
        require(initialCount > 0)
    }

    /*
     * The current count of the latch, which is decremented when countDownAndAwait is called.
     * When the count reaches zero, coroutines suspended on this latch can proceed. The count is then reset to
     * the initial count.
     */
    private var count = initialCount

    /**
     * The mutex used to guard access to the latch state and to implement the synchronizer.
     */
    private val guard = Mutex()

    /**
     * The list of suspended coroutines.
     */
    private var suspended = mutableListOf<Continuation<Unit>>()

    /**
     * Decrements the count of the latch and suspends the caller until the count reaches zero. The count then resets
     * to its initial value.
     */
    suspend fun countDownAndAwait(): Unit {
        guard.lock()
        if (--count == 0) {
            count = initialCount
            val toContinue = suspended
            suspended = mutableListOf()
            guard.unlock()
            toContinue.forEach { it.resume(Unit) }
        } else {
            suspendCoroutine { continuation ->
                suspended.add(continuation)
                guard.unlock()
            }
        }
    }
}
