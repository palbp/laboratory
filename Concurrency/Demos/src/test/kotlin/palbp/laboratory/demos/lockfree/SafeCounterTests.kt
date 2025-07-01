package palbp.laboratory.demos.lockfree

import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentHashMap
import kotlin.test.assertEquals

// Number of threads used on each test
private const val N_OF_THREADS = 6

// Number of repetitions performed by each thread
private const val N_OF_REPS = 100_000

class SafeCounterTests {
    @Test
    fun `increments are not lost`() {
        val sharedCounter = LockFreeCounter(0)

        (0 until N_OF_THREADS).map {
            Thread {
                repeat(N_OF_REPS) { sharedCounter.increment() }
            }.apply(Thread::start)
        }.forEach(Thread::join)

        assertEquals(expected = N_OF_REPS * N_OF_THREADS, actual = sharedCounter.value)
    }

    @Test
    fun `increment produces the original value plus one`() {
        val allCounterValues = ConcurrentHashMap<Int, Boolean>()
        val sharedCounter = LockFreeCounter(0)
        (0 until N_OF_THREADS).map {
            Thread {
                val counterValues = HashSet<Int>()
                repeat(N_OF_REPS) {
                    counterValues.add(sharedCounter.increment())
                }
                counterValues.forEach {
                    allCounterValues[it] = true
                }
            }.apply(Thread::start)
        }.forEach(Thread::join)

        assertEquals(expected = N_OF_REPS * N_OF_THREADS, actual = allCounterValues.size)
    }
}
