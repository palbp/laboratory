package palbp.laboratory.demos.synch

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
class UnboundedQueue<T> {

    /**
     * The actual queue.
     */
    private val items = mutableListOf<T>()

    private class Request<T>(var item: T? = null, val privateCondition: Condition)
    private val pendingRequests = mutableListOf<Request<T>>()

    // The monitor's lock. Each waiting thread has its own condition
    private val mLock: Lock = ReentrantLock()

    private fun notifyConsumer() {
        if (pendingRequests.isNotEmpty()) {
            val item = items.removeFirst()
            val request = pendingRequests.removeFirst()
            request.item = item
            request.privateCondition.signal()
        }
    }

    /**
     * Adds the given element to the end of the queue.
     * @param elem The element to be added to the queue
     */
    fun put(item: T) {
        mLock.withLock {
            items.add(item)
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
    fun take(timeout: Long, unit: TimeUnit): T? {
        mLock.withLock {
            if (items.isNotEmpty())
                return items.removeFirst()

            val myRequest = Request<T>(privateCondition = mLock.newCondition())
            pendingRequests.add(myRequest)

            var remainingTime = unit.toNanos(timeout)
            while (true) {
                try {
                    remainingTime = myRequest.privateCondition.awaitNanos(remainingTime)
                }
                catch (ie: InterruptedException) {
                    pendingRequests.remove(myRequest)
                }

                if (myRequest.item != null)
                    return myRequest.item

                if (remainingTime <= 0)
                    return null
            }
        }
    }
}