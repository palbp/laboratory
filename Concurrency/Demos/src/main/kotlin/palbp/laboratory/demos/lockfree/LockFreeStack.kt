package palbp.laboratory.demos.lockfree

import java.util.concurrent.atomic.AtomicReference

/**
 * Lock free implementation of a stack.
 * The implementation uses Treiber's algorithm (https://en.wikipedia.org/wiki/Treiber_stack)
 */
class LockFreeStack<T> {
    private class Node<T>(val item: T, var next: Node<T>? = null)

    private val top = AtomicReference<Node<T>>()

    fun push(item: T) {
        val newTop = Node(item)
        do {
            val observedTop = top.get()
            newTop.next = observedTop
        } while (!top.compareAndSet(observedTop, newTop))
    }

    fun pop(): T? {
        var observedTop: Node<T>? = null
        do {
            observedTop = top.get()
            if (observedTop == null) {
                return null
            }
            val newTop = observedTop.next
        } while (!top.compareAndSet(observedTop, newTop))
        return observedTop?.item
    }
}
