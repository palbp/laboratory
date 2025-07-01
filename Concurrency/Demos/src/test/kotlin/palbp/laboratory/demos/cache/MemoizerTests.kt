package palbp.laboratory.demos.cache

import org.slf4j.LoggerFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random
import kotlin.test.Test

fun someExpensiveComputation(args: Int): Int {
    Thread.sleep(200)
    return args
}

const val N_THREADS = 6
const val TIME_BUDGET_MS = 20 * 1000L

private val log = LoggerFactory.getLogger(MemoizerTests::class.java)

class MemoizerTests {
    val pool = Executors.newFixedThreadPool(N_THREADS)

    fun doIt(computation: Computation<Int, Int>) {
        log.info("Starting with time budget of $TIME_BUDGET_MS ms.")
        val callCount = AtomicLong(0)
        val terminate = AtomicBoolean(false)
        val done = CountDownLatch(N_THREADS)
        repeat(N_THREADS) {
            pool.submit {
                var accumulate = 0L
                val randomGenerator = Random(System.nanoTime())
                var myCallCount = 0L
                while (!terminate.get()) {
                    val args = randomGenerator.nextInt(0, 100)
                    accumulate += computation(args)
                    myCallCount += 1
                }
                log.info("callCount = $myCallCount, accumulate = $accumulate")
                callCount.addAndGet(myCallCount)
                done.countDown()
            }
        }

        Thread.sleep(TIME_BUDGET_MS)
        log.info("Time budget of $TIME_BUDGET_MS ms ended. Terminating...")

        terminate.set(true)
        done.await()
        log.info("Accumulated call count was ${callCount.get()}")
    }

    @Test
    fun `tests throughput with non scalable memoization`() {
        doIt(memoize(MemoizerType.NON_SCALABLE, ::someExpensiveComputation))
    }

    @Test
    fun `tests throughput with scalable memoization`() {
        doIt(memoize(MemoizerType.BEST_IN_CLASS, ::someExpensiveComputation))
    }

    @Test
    fun `tests throughput with my memoization 1`() {
        doIt(memoize(MemoizerType.FOR_FUN_1, ::someExpensiveComputation))
    }

    @Test
    fun `tests throughput with my memoization 2`() {
        doIt(memoize(MemoizerType.FOR_FUN_2, ::someExpensiveComputation))
    }

    @Test
    fun `tests throughput with my memoization 3`() {
        doIt(memoize(MemoizerType.FOR_FUN_3, ::someExpensiveComputation))
    }
}
