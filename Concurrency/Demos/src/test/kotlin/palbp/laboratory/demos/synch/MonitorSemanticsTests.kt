package palbp.laboratory.demos.synch

import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.test.assertTrue

class MonitorSemanticsTests {
    @Test
    fun `a signaled thread cannot be interrupted`() {
        val resultToAssert = CompletableFuture<Boolean>()
        val sutLock = ReentrantLock()
        val sutCondition = sutLock.newCondition()
        val t = Thread {
            try {
                sutLock.withLock { sutCondition.await() }
                resultToAssert.complete(Thread.interrupted())
            }
            catch (t: Throwable) {
                resultToAssert.completeExceptionally(t)
            }
        }.apply { start() }

        Thread.sleep(1000)
        sutLock.withLock {
            t.interrupt()
            sutCondition.signal()
        }
        t.join()
        assertTrue(resultToAssert.get())
    }

    @Test
    fun `several consecutive signals unblock the corresponding number of threads`() {
        val threadCount = 4
        val completed = CountDownLatch(threadCount)

        val lock = ReentrantLock()
        val condition = lock.newCondition()

        repeat(threadCount) {
            Thread {
                lock.withLock {
                    assertTrue(condition.await(5, TimeUnit.SECONDS))
                    completed.countDown()
                }
            }.apply { start() }
        }

        Thread.sleep(1000)
        lock.withLock {
            repeat(threadCount) {
                condition.signal()
            }
        }
        completed.await()
    }
}