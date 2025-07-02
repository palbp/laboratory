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
class CyclicCountDownLatchBKS(private val initialCount: Int) {
    init {
        require(initialCount > 0) { "Initial count must be greater than zero." }
    }

    private val guard = ReentrantLock()
    private val condition = guard.newCondition()

    private var count = initialCount

    data class Request(var signalled: Boolean = false, var batchSize: Int = 0)
    private var sharedRequest: Request? = null

    @Throws(InterruptedException::class)
    fun countDownAndAwait(timeout: Duration): Boolean {
        guard.withLock {
            if (--count == 0) {
                count = initialCount
                signalAllWaitingThreads()
                return true
            }

            val myRequest = sharedRequest ?: Request()
            if (sharedRequest == null) sharedRequest = myRequest
            myRequest.batchSize += 1

            var remaining = timeout.inWholeNanoseconds

            try {
                while (true) {

                    remaining = condition.awaitNanos(remaining)

                    if (myRequest.signalled) {
                        return true
                    }

                    if (remaining <= 0) {
                        myRequest.batchSize -= 1
                        if (myRequest.batchSize == 0) {
                            sharedRequest = null
                        }
                        return false
                    }
                }
            }
            catch (ie: InterruptedException) {
                myRequest.batchSize -= 1
                if (myRequest.batchSize == 0) {
                    sharedRequest = null
                }

                Thread.currentThread().interrupt()
                throw ie
            }
        }
    }

    private fun signalAllWaitingThreads() {
        val currentBatch = sharedRequest
        sharedRequest = null
        currentBatch?.let { it.signalled = true }
        condition.signalAll()
    }
}