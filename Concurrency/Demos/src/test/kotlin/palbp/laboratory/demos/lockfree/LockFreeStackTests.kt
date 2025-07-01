package palbp.laboratory.demos.lockfree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// Number of threads used on each test
private const val N_OF_THREADS = 6

// Number of repetitions performed by each thread
private const val N_OF_REPS = 1_000_000

class LockFreeStackTests {
    @Test
    fun `multiple threads pushing to a shared stack`() {
        val sharedStack = LockFreeStack<Int>()

        (0 until N_OF_THREADS).map {
            Thread {
                repeat(N_OF_REPS) {
                    sharedStack.push(it)
                }
            }.apply(Thread::start)
        }.forEach(Thread::join)

        var poppedCounter = 0
        do {
            val poppedValue = sharedStack.pop()
            if (poppedValue != null) poppedCounter += 1
        } while (poppedValue != null)

        assertEquals(expected = N_OF_REPS * N_OF_THREADS, actual = poppedCounter)
    }
}
