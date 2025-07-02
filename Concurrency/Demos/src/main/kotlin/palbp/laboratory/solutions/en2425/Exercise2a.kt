package palbp.laboratory.solutions.en2425

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

/**
 * 2. [5] Implement the CyclicCountDownLatch synchronizer with the following interface.
 *
 * <pre> {@code
 * class CyclicCountDownLatch(val initialCount: Int) {
 *    init { require(initialCount > 0) }
 *    @Throws(InterruptedException::class)
 *    fun countDownAndAwait(timeout: Duration): Boolean
 * }
 * }</pre>
 *
 * The countDownAndAwait function decrements the counter and waits for it to reach zero.
 * If the counter reaches zero, it must be immediately reset to initialCount, and all threads blocked
 * in countDownAndAwait should resume execution, returning true. The countDownAndAwait function returns false
 * if the timeout duration time is reached, and should also be sensitive to thread interruption,
 * handling it according to the Java platform interruption protocol.
 *
 * Script:
 * 1 - Start with a flawed implementation of the CyclicCountDownLatch class, to illustrate the challenges of
 * implementing synchronizers that can transition from the signalled state to the unsignalled state.
 * 2 - Implement a solution using the kernel-style approach [CyclicCountDownLatchKS]
 * 3 - Implement a solution using the batched kernel-style approach [CyclicCountDownLatchBKS]
 * 4 - Implement a solution using an approach where we count the number of cycles (a.k.a. generations) [CyclicCountDownLatch]
 */
class CyclicCountDownLatchKS(private val initialCount: Int) {
    init {
        require(initialCount > 0) { "Initial count must be greater than zero." }
    }

    private var count = initialCount

    private val guard = ReentrantLock()
    private val condition = guard.newCondition()

    private data class Request(var signalled: Boolean = false)
    private val waiting = mutableListOf<Request>()

    @Throws(InterruptedException::class)
    fun countDownAndAwait(timeout: Duration): Boolean {
        guard.withLock {
            if (--count == 0) {
                count = initialCount
                signalAllWaitingThreads()
                return true
            }

            val myRequest = Request()
            waiting.add(myRequest)

            try {
                var remaining = timeout.inWholeNanoseconds
                while (true) {

                    remaining = condition.awaitNanos(remaining)

                    if (myRequest.signalled) {
                        return true
                    }

                    if (remaining <= 0) {
                        // Give up (timeout)
                        waiting.remove(myRequest)
                        return false
                    }
                }
            }
            catch (ie: InterruptedException) {
                waiting.remove(myRequest)
                throw ie
            }
        }
    }

    private fun signalAllWaitingThreads() {
        while(waiting.isNotEmpty()) {
            waiting.removeFirst().signalled = true
        }
        condition.signalAll()
    }
}