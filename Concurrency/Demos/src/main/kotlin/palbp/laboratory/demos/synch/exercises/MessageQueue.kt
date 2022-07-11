package palbp.laboratory.demos.synch.exercises

import java.lang.Thread.currentThread
import java.util.LinkedList
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

/**
 * A possible solution for the following exercise.
 *
 * Implement the message queue synchronizer, to support communication between producer and consumer threads
 * through messages of generic type T. The communication must use the FIFO criterion (first in first out).
 * The public interface of this synchronizer is as follows:
 *
 * class MessageQueue<T>() {
 *    fun enqueue(message: T): Unit { … }
 *    @Throws(InterruptedException::class)
 *    fun tryDequeue(nOfMessages: Int, timeout: Duration): List<T>? { … }
 * }
 *
 * The [enqueue] method delivers a message to the queue without blocking the caller thread. The [tryDequeue] method
 * attempts to remove [nOfMessages] messages from the queue, blocking the invoking thread as long as:
 * 1) this operation cannot complete successfully, or
 * 2) the timeout time set for the operation does not expire, or
 * 3) the thread is not interrupted.
 * Note that message removal cannot be performed partially, i.e. either [nOfMessages] messages are removed or
 * no messages are removed. These removal operations must be completed on a first-come, first-served basis,
 * regardless of the values [nOfMessages]. Be aware of the consequences of giving up (either through cancellation
 * or timeout) on a [tryDequeue] operation.
 */
class MessageQueue<T> {

    private class DequeueRequest<T>(
        val nOfMessages: Int,
        val condition: Condition,
        var messages: List<T>? = null
    )

    private val guard = ReentrantLock()

    private val dequeueRequests = LinkedList<DequeueRequest<T>>()
    private val messages = LinkedList<T>()

    fun enqueue(message: T) {
        guard.withLock {
            messages.addLast(message)
            tryCompletePendingDequeues()
        }
    }

    @Throws(InterruptedException::class)
    fun tryDequeue(nOfMessages: Int, timeout: Duration): List<T>? {
        guard.withLock {
            if (dequeueRequests.isEmpty() && messages.size >= nOfMessages)
                return takeMessages(nOfMessages)

            val myRequest = DequeueRequest<T>(nOfMessages, guard.newCondition())
            var remainingTime = timeout.inWholeNanoseconds

            try {
                while (true) {
                    remainingTime = myRequest.condition.awaitNanos(remainingTime)

                    if (myRequest.messages != null)
                        return myRequest.messages

                    if (remainingTime <= 0) {
                        dequeueRequests.remove(myRequest)
                        tryCompletePendingDequeues()
                        return null
                    }
                }
            }
            catch (ie: InterruptedException) {
                if(dequeueRequests.remove(myRequest)) {
                    tryCompletePendingDequeues()
                    throw ie
                }
                currentThread().interrupt()
                return myRequest.messages
            }
        }
    }

    private fun takeMessages(nOfMessages: Int) = List(nOfMessages) {
        messages.removeFirst()
    }

    private fun tryCompletePendingDequeues() {
        while(messages.size >= dequeueRequests.first.nOfMessages) {
            val completedRequest = dequeueRequests.removeFirst()
            completedRequest.messages = takeMessages(completedRequest.nOfMessages)
            completedRequest.condition.signal()
        }
    }
}
