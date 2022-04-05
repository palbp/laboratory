package palbp.laboratory.demos.synch;

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Class whose instances represent <i>manual reset events</i>.
 *
 * This synchronization object has the same behavior as the one provided in Windows.
 * Also known as <i>gate</i>.
 *
 * Gate open 	<=> manual reset event signaled
 * Gate closed 	<=> manual reset event not signaled
 *
 *  It is a flawed implementation. To understand the implementation flaw imagine the
 *  following scenario: calls to set - reset in rapid succession. What would happen?
 */
class ManualResetEventFlawed(val initialSignaledState: Boolean) {

    /**
     * Holds information on whether the object is signaled (true) or not (false).
     */
    private var signaled = initialSignaledState

    // The monitor used by the synchronizer
    private val mLock: Lock = ReentrantLock()
    private val mCondition: Condition = mLock.newCondition()

    /**
     * Sets the manual reset event to the signaled state. Waiting threads are unblocked.
     */
    fun set() {
        mLock.withLock {
            if(!signaled) {
                signaled = true
                mCondition.signalAll()
            }
        }
    }

    /**
     * Sets the manual reset event to the non signaled state.
     */
    fun reset() {
        mLock.withLock {
            if(signaled)
                signaled = false;
        }
    }

    /**
     * Blocks the calling thread until the event becomes signaled or the specified time as elapsed.
     *
     * @return A boolean value indicating the return reason: true, the event was signaled; false, the
     * specified time interval has elapsed.
     * @throws InterruptedException If the blocked thread has been signaled for cancellation.
     */
    @Throws(InterruptedException::class)
    fun waitOne(timeout: Long, unit: TimeUnit): Boolean {
        mLock.withLock {
            // Check event signaled state.
            if(signaled)
                return true

            // Otherwise, block calling thread until the required conditions are met
            var remainingTime = unit.toNanos(timeout)
            while(true) {
                remainingTime = mCondition.awaitNanos(remainingTime)

                if(signaled)
                    return true

                if(remainingTime <= 0)
                    return false
            }
        }
    }
}
