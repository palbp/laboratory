package palbp.laboratory.demos.synch

import java.util.LinkedList
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Class whose instances represent <i>unbounded queues</i>.
 *
 * An <i>unbounded queue</i> is a synchronization object used to support communication
 * between threads that produce items to be used (consumed) by other threads. The former are
 * named <i>producers</i>, the latter are named <i>consumers</i>.
 *
 * This type of synchronization object is useful when the expected production rate is lower than
 * the rate of consumption. In such scenario, memory exhaustion provoked by the absence of bounds
 * does not occur.
 *
 * Notice that this implementation enforces a FIFO ordering while servicing blocked threads.
 */
class UnboundedQueueKS<T> {
    /**
     * The actual queue.
     */
    private val items = LinkedList<T>()

    private class Request<T>(var item: T? = null)

    private val pendingRequests = LinkedList<Request<T>>()

    // The monitor's lock and condition
    private val mLock: Lock = ReentrantLock()
    private val mCondition: Condition = mLock.newCondition()

    private fun notifyConsumer() {
        if (pendingRequests.isNotEmpty()) {
            val item = items.removeFirst()
            val request = pendingRequests.removeFirst()
            request.item = item
            mCondition.signalAll()
        }
    }

    /**
     * Adds the given element to the end of the queue.
     * @param elem The element to be added to the queue
     */
    fun put(item: T) {
        mLock.withLock {
            items.addLast(item)
            notifyConsumer()
        }
    }

    /**
     * Removes an element from the queue. The calling thread is blocked until an element becomes available or
     * the specified time as elapsed. Threads are serviced in a FIFO ordering, meaning, upon the item is delivered to
     * the longest waiting thread.
     *
     * @return The element removed from the queue or <code>null</code> if the specified time elapses
     * before an element becomes available.
     * @throws InterruptedException If the blocked thread has been signaled for cancellation.
     */
    @Throws(InterruptedException::class)
    fun take(
        timeout: Long,
        unit: TimeUnit,
    ): T? {
        mLock.withLock {
            if (items.isNotEmpty()) {
                return items.removeFirst()
            }

            val myRequest = Request<T>()
            pendingRequests.addLast(myRequest)

            var remainingTime = unit.toNanos(timeout)
            while (true) {
                try {
                    remainingTime = mCondition.awaitNanos(remainingTime)
                } catch (ie: InterruptedException) {
                    pendingRequests.remove(myRequest)
                    throw ie
                }

                if (myRequest.item != null) {
                    return myRequest.item
                }

                if (remainingTime <= 0) {
                    pendingRequests.remove(myRequest)
                    return null
                }
            }
        }
    }
}
