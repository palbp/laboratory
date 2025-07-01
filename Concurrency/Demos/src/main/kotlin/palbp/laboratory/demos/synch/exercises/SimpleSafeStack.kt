package palbp.laboratory.demos.synch.exercises

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * A simple stack implementation that is safe to use in a multithreaded environment.
 * The stack allows pushing and popping elements in a thread-safe manner.
 * @param T the type of elements in the stack
 */
class SimpleSafeStack<T> {
    private class Node<T>(val value: T, val next: Node<T>?)

    private var head: Node<T>? = null
    private val guard = ReentrantLock()

    /**
     * Pushes a new value onto the stack.
     * @param value the value to be pushed onto the stack
     */
    fun push(value: T): Unit =
        guard.withLock {
            head = Node(value, head)
        }

    /**
     * Pops a value from the stack.
     * @return the value popped from the stack, or null if the stack is empty
     */
    fun pop(): T? =
        guard.withLock {
            head?.let {
                head = it.next
                it.value
            }
        }
}
