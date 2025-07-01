package palbp.laboratory.demos.synch.exercises

import java.util.concurrent.Executor
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * A possible solution for the following exercise.
 *
 * Implement the following function:
 * <code> fun <A,B,C> run(f0: ()->A, f1: ()->B, f2: (A,B)->C, executor: Executor): C </code>
 * The function returns the value of the expression f2(f0(), f1()), with the following requirements:
 * 1) all functions must be executed by the executor, and not on the thread that called run;
 * 2) the potential parallelism existing in the expression should be explored, assuming that the relative order
 * of evaluation of f0() and f1() is not relevant.
 * For the sake of simplification, assume that none of the functions f0, f1, and f2 throw exceptions.
 * Keep in mind that for satisfying the synchronization requirements, solutions can only make use of explicit monitors
 * (i.e. Lock and Condition implementations).
 */
@Suppress("UNCHECKED_CAST")
fun <A, B, C> run(
    f0: () -> A,
    f1: () -> B,
    f2: (A, B) -> C,
    executor: Executor,
): C {
    /**
     * The implementation of this synchronizer relies on it being local to the function.
     * Note that it doesn't try to enforce its contract. It presumes that the using code respects it. If the class
     * was to be made available to other code, this approach would be unacceptable. Instead, we would have to use a
     * defensive approach so that the offending code would receive exceptions upon improper use.
     */
    class ResultsHolder(val expectedResults: Int) {
        private val guard = ReentrantLock()
        private val hasAllResults = guard.newCondition()

        private val results: Array<Any?> = Array(expectedResults) { null }
        private var publishedResults = 0

        fun putResultAt(
            index: Int,
            value: Any?,
        ) {
            guard.withLock {
                results[index] = value
                publishedResults += 1
                if (publishedResults == expectedResults) {
                    hasAllResults.signalAll()
                }
            }
        }

        fun waitForResults(): Array<Any?> {
            guard.withLock {
                while (publishedResults != expectedResults)
                    hasAllResults.await()
                return results
            }
        }
    }

    val pairHolder = ResultsHolder(expectedResults = 2)
    val aIndex = 0
    val bIndex = 1
    executor.execute { pairHolder.putResultAt(aIndex, value = f0()) }
    executor.execute { pairHolder.putResultAt(bIndex, value = f1()) }

    val inputPair = pairHolder.waitForResults()

    val resultHolder = ResultsHolder(expectedResults = 1)
    val cIndex = 0
    executor.execute {
        val cResult = f2(inputPair[aIndex] as A, inputPair[bIndex] as B)
        resultHolder.putResultAt(cIndex, cResult)
    }

    return resultHolder.waitForResults()[cIndex] as C
}
