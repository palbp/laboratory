package palbp.laboratory.solutions.en2425

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


/**
 * 1. [2] Consider the UnsafeStack class, containing a non-thread-safe implementation.
 * Create a thread-safe version of this class using locks.
 *
 * <pre> {@code
 * class UnsafeStack<T> {
 *     class Node<T>(val value: T, val next: Node<T>?)
 *     private var head: Node<T>? = null
 *
 *     fun push(value: T) {
 *         head = Node(value, head)
 *     }
 *
 *     fun pop(): T? {
 *         val observedHead = head
 *         return if(observedHead != null) {
 *             head = observedHead.next
 *             observedHead.value
 *         } else {
 *             null
 *         }
 *     }
 * }
 * }</pre>
 *
 * Script:
 * 1 - Present the simplistic implementation of the SafeStack class (i.e. add a lock to the UnsafeStack class).
 * 2 - Discuss the common pitfall of reading the head of the stack without holding the lock.
 * 3 - Refine the implementation to make it more compact
 */
class SafeStack<T> {
    private class Node<T>(val value: T, val next: Node<T>?)
    private var head: Node<T>? = null
    private val guard = ReentrantLock()

    /**
     * Pushes a new value onto the stack.
     * @param value the value to be pushed onto the stack
     */
    fun push(value: T) {
        guard.withLock {
            head = Node(value, head)
        }
    }

    /**
     * Pops a value from the stack.
     * @return the value popped from the stack, or null if the stack is empty
     */
    fun pop(): T? {
        guard.withLock {
            val observedHead = head
            return if(observedHead != null) {
                head = observedHead.next
                observedHead.value
            } else {
                null
            }
        }
    }
}