package palbp.laboratory.solutions.en2425

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * [4] Implement the CyclicCountDownLatch synchronizer, for use with coroutines, with the following interface.
 *
 * <pre> {@code
 * class CyclicCountDownLatch(val initialCount: Int) {
 *    init { require(initialCount > 0) }
 *    suspend fun countDownAndAwait(): Unit
 * }
 * }</pre>
 *
 * The countDownAndAwait function decrements the counter and waits for it to reach zero. If the counter reaches zero,
 * it must be immediately reset to initialCount, and all coroutines that are currently waiting in countDownAndAwait
 * should resume execution. The countDownAndAwait function does not support cancellation.
 *
 * Script:
 * 1 - Implement the solution while making an analogy with the kernel-style approach used in the classic synchronizers.
 * 2 - Discuss possible caveats:
 *      - no open calls to suspend functions while holding the lock (cannot suspend while holding the lock)
 *      - call to the resume operation without holding the lock (also an open call)
 * 3 - Discuss the alternative solution with a ReentrantLock, instead of using a Mutex.
 */
class CyclicCountDownLatchSuspending(val initialCount: Int) {
    init { require(initialCount > 0) }

    private val guard = Mutex()
    private var continuations: MutableList<Continuation<Unit>> = mutableListOf()

    private var count = initialCount

    suspend fun countDownAndAwait(): Unit {
        guard.lock()
        if (--count == 0) {
            count = initialCount
            val toResume = continuations
            continuations = mutableListOf()
            guard.unlock()

            toResume.forEach { it.resume(Unit) }
            return
        }

        suspendCoroutine { continuation ->
            continuations.add(continuation)
            guard.unlock()
        }
    }
}
