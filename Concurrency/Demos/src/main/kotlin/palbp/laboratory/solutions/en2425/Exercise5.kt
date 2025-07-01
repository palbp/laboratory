package palbp.laboratory.solutions.en2425

import kotlinx.coroutines.runBlocking
import palbp.laboratory.demos.coroutines.exercises.PeriodicMessage
import palbp.laboratory.demos.coroutines.exercises.invoke
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration


/**
 * [4] Consider the following interface from the Java standard class library.
 *
 * <pre> {@code
 * public interface Executor { void execute(Runnable command); }
 * }</pre>
 *
 * Implement the following extension function
 *
 * <pre> {@code
 * suspend fun <T1,T2> Executor.invoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2>
 * }</pre>
 *
 * This function promotes the execution of functions f1 and f2 in the thread pool represented by the Executor
 * passed as the receiver, taking advantage of the multiple worker threads it may have. The result is a pair
 * containing the results produced by each evaluation of f1 and f2. The invoke function must not block the thread
 * in which it is called. Assume that the evaluations of f1 and f2 do not throw exceptions.
 *
 * Script:
 * 1 - Present a solution that uses a custom synchronizer (PairHolder) to store the results of f1 and f2.
 * 2 - 
 */

class PairHolder<T1, T2> {

    private var first : T1? = null
    private var second : T2? = null

    private val guard = ReentrantLock()

    fun setFirst(value: T1): Pair<T1, T2>? =
        guard.withLock {
            first = value
            second?.let { Pair(first = value, second = it) }
        }

    fun setSecond(value: T2): Pair<T1, T2>? =
        guard.withLock {
            second = value
            first?.let { Pair(first = it, second = value) }
        }
}

suspend fun <T1,T2> Executor.invoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2> =
    suspendCoroutine { continuation ->
        val holder: PairHolder<T1, T2> = PairHolder()
        this.execute {
            holder.setFirst(f1())?.let {
                continuation.resume(it)
            }
        }
        this.execute {
            holder.setSecond(f2())?.let {
                continuation.resume(it)
            }
        }
    }

fun main() = runBlocking {
    val executor = Executors.newCachedThreadPool()
    val result = executor.invoke(
        f1 = { Thread.sleep(5000); 42 },
        f2 = { Thread.sleep(2000); 24 }
    )
    println("Result: $result") // Should print: Result: (42, 24)
    executor.shutdown()
}
