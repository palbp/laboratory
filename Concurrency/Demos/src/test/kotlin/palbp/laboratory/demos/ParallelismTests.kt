package palbp.laboratory.demos

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min
import kotlin.system.measureTimeMillis
import kotlin.test.Test

private const val N_OF_THREADS = 6
private const val BLOCK_SIZE = 4*1024*1024
private const val REPETITIONS = 1


class ParallelismTests {

    @Test
    fun `accumulate serial`() {
        val values = IntArray(N_OF_THREADS * BLOCK_SIZE) { it }
        var accumulator = 0
        var minElapsed = Long.MAX_VALUE
        repeat(REPETITIONS) {
            accumulator = 0
            val elapsed = measureTimeMillis {
                for (i in values.indices)
                    accumulator += values[i]
            }
            minElapsed = min(minElapsed, elapsed)
        }
        println("Accumulate Serial took $minElapsed ms. Accumulator is $accumulator")
    }

    @Test
    fun `accumulate serial with unnecessary Atomic`() {
        val values = IntArray(N_OF_THREADS * BLOCK_SIZE) { it }
        val accumulator = AtomicInteger(0)
        var minElapsed = Long.MAX_VALUE
        repeat(REPETITIONS) {
            accumulator.set(0)
            val elapsed = measureTimeMillis {
                for (i in values.indices)
                    accumulator.addAndGet(values[i])
            }
            minElapsed = min(minElapsed, elapsed)
        }
        println("Accumulate Serial with unnecessary Atomic took $minElapsed ms. Accumulator is $accumulator")
    }

    @Test
    fun `accumulate parallel with shared atomic`() {
        val values = IntArray(N_OF_THREADS * BLOCK_SIZE) { it }
        val accumulator = AtomicInteger(0)
        var minElapsed = Long.MAX_VALUE

        repeat(REPETITIONS) {
            val go = CountDownLatch(1);
            val done = CountDownLatch(N_OF_THREADS);
            accumulator.set(0)
            repeat(N_OF_THREADS) {
                val firstPos = it * BLOCK_SIZE
                Thread {
                    go.await()
                    for (index in firstPos until firstPos + BLOCK_SIZE)
                        accumulator.addAndGet(values[index])
                    done.countDown()
                }.start()
            }
            Thread.sleep(500)
            val elapsed = measureTimeMillis {
                go.countDown()
                done.await()
            }
            minElapsed = min(minElapsed, elapsed)
        }
        println("Accumulate Parallel with shared Atomic took $minElapsed ms. Accumulator is $accumulator")
    }

    @Test
    fun `accumulate parallel with private accumulator`() {
        val values = IntArray(N_OF_THREADS * BLOCK_SIZE) { it }
        val accumulator = AtomicInteger(0)
        var minElapsed = Long.MAX_VALUE

        repeat(REPETITIONS) {
            val go = CountDownLatch(1);
            val done = CountDownLatch(N_OF_THREADS);
            accumulator.set(0)
            repeat(N_OF_THREADS) {
                val firstPos = it * BLOCK_SIZE
                Thread {
                    go.await()
                    var privateAccumulator = 0
                    for (index in firstPos until firstPos + BLOCK_SIZE)
                        privateAccumulator += values[index]
                    accumulator.addAndGet(privateAccumulator)
                    done.countDown()
                }.start()
            }
            Thread.sleep(500)
            val elapsed = measureTimeMillis {
                go.countDown()
                done.await()
            }
            minElapsed = min(minElapsed, elapsed)
        }
        println("Accumulate Parallel with private accumulator took $minElapsed ms. Accumulator is $accumulator")
    }
}
