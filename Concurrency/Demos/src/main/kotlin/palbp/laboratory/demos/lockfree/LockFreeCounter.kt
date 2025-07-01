package palbp.laboratory.demos.lockfree

import java.util.concurrent.atomic.AtomicInteger

@Suppress("ControlFlowWithEmptyBody")
class LockFreeCounter(initialValue: Int = 0) {
    private val _value = AtomicInteger(initialValue)

    val value: Int
        get() = _value.get()

    private fun doSafely(operation: (Int) -> Int): Int {
        while (true) {
            val observedValue = _value.get()
            val newValue = operation(observedValue)
            if (_value.compareAndSet(observedValue, newValue)) {
                return newValue
            }
        }
    }

    fun increment(): Int = doSafely { it + 1 }

    fun decrement(): Int = doSafely { it - 1 }
}
