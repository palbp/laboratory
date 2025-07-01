package palbp.laboratory.demos.synch

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class Latch {
    private var signaled: Boolean = false

    private val mLock: Lock = ReentrantLock()
    private val mCondition: Condition = mLock.newCondition()

    @Throws(InterruptedException::class)
    fun await(
        timeout: Long,
        unit: TimeUnit,
    ): Boolean {
        mLock.withLock {
            if (signaled) {
                return true
            }

            var remainingTime = unit.toNanos(timeout)
            while (true) {
                remainingTime = mCondition.awaitNanos(remainingTime)

                if (signaled) {
                    return true
                }

                if (remainingTime <= 0) {
                    return false
                }
            }
        }
    }

    @Throws(InterruptedException::class)
    fun await() {
        mLock.withLock {
            while (!signaled) {
                mCondition.await()
            }
        }
    }

    fun signal() {
        mLock.withLock {
            signaled = true
            mCondition.signalAll()
        }
    }
}
