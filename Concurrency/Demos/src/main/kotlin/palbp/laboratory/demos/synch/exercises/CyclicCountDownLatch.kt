package palbp.laboratory.demos.synch.exercises

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

/**
 * A cyclic countdown latch that allows threads to wait until a certain count is reached,
 * and then resets the count to allow for repeated use.
 * @param initialCount the initial count for the latch; must be greater than 0.
 */
class CyclicCountDownLatch(val initialCount: Int) {
    init {
        require(initialCount > 0)
    }

    /*
     * The current count of the latch, which is decremented when countDownAndAwait is called.
     * When the count reaches zero, threads waiting on this latch can proceed. The count is then reset to
     * the initial count.
     */
    private var count = initialCount

    /**
     * Indicates the generation of the latch. This is used to differentiate between threads waiting on different
     * generations of the latch. The generation is incremented each time the count reaches zero.
     */
    private var generation = 0

    /**
     * The monitor used to implement the synchronizer
     */
    private val guard = ReentrantLock()
    private val condition = guard.newCondition()

    /**
     * Decrements the count of the latch. If the count reaches zero, it signals all waiting threads and resets the count
     * to the initial value.
     * @param timeout the maximum time to wait for the count to reach zero
     * @return true if the count reached zero and the waiting thread (i.e. the caller) was signaled,
     * false if the timeout was reached without the count reaching zero
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun countDownAndAwait(timeout: Duration): Boolean {
        guard.withLock {
            if (--count == 0) {
                generation += 1
                count = initialCount
                condition.signalAll()
                return true
            }

            val myGeneration = generation
            var remainingTime = timeout.inWholeNanoseconds
            while (true) {
                remainingTime = condition.awaitNanos(remainingTime)

                if (myGeneration < generation) {
                    return true
                }

                if (remainingTime <= 0) {
                    return false
                }
            }
        }
    }
}
