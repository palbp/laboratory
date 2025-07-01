package palbp.laboratory.demos.coroutines.exercises

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  Possible solution for the following exercise.
 *
 *  Implement the function with the following signature:
 *
 *  suspend fun race(f0: suspend () -> Int, f1: suspend () -> Int): Int
 *
 *  This function executes the functions passed as an argument in parallel, returning the value returned by the first
 *  function to finish successfully. An execution of the race function should only end when the coroutines created in
 *  its scope have finished. However, the race function must terminate as quickly as possible, by canceling the still
 *  running coroutine, after one of the functions passed as an argument has successfully completed.
 */
suspend fun race(
    f0: suspend () -> Int,
    f1: suspend () -> Int,
): Int {
    val guard = Mutex()
    var firstResult: Int? = null

    try {
        coroutineScope {
            val parent = this
            launch {
                val result = f0()
                guard.withLock {
                    if (firstResult == null) {
                        firstResult = result
                        parent.cancel()
                    }
                }
            }

            launch {
                val result = f1()
                guard.withLock {
                    if (firstResult == null) {
                        firstResult = result
                        parent.cancel()
                    }
                }
            }
        }
    } catch (_: CancellationException) {
    }
    return guard.withLock { firstResult as Int }
}


/**
 * Possible solution for the following exercise.
 *
 * Consider the following interface from the Java standard class library
 *   public interface Executor { void execute(Runnable command); }
 *
 * Implement the following extension function
 *
 * suspend fun <T1,T2> Executor.invoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2>
 *
 * This function promotes the execution of functions f1 and f2 in the thread pool represented by the
 * Executor passed as the receiver, taking advantage of the multiple worker threads it may have.
 * The result is a pair containing the results produced by each evaluation of f1 and f2.
 * The invoke function must not block the thread in which it is called.
 * Assume that the evaluations of f1 and f2 do not throw exceptions.
 */

class PairHolder<T1, T2> {
    private var first: T1? = null
    private var second: T2? = null
    private val guard = ReentrantLock()

    fun setFirst(value: T1): Pair<T1, T2>? = guard.withLock {
        require(first == null) { "First value is already set" }
        first = value
        second?.let { Pair(first = value, second = it) }
    }

    fun setSecond(value: T2): Pair<T1, T2>? = guard.withLock {
        require(second == null) { "Second value is already set" }
        second = value
        first?.let { Pair(first = it, second = value) }
    }
}

suspend fun <T1,T2> Executor.invoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2> = suspendCoroutine { continuation ->
    val holder = PairHolder<T1, T2>()
    execute { with(holder) { setFirst(f1())?.let { continuation.resume(it) } } }
    execute { with(holder) { setSecond(f2())?.let { continuation.resume(it) } } }
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

suspend fun <T1,T2> Executor.otherInvoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2> = coroutineScope {
    val job1 = async {
        suspendCoroutine {
            execute { it.resume(f1()) }
        }
    }
    val job2 = async {
        suspendCoroutine {
            execute { it.resume(f2()) }
        }
    }
    Pair(job1.await(), job2.await())
}

suspend fun <T1,T2> Executor.yetAnotherInvoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2> = coroutineScope {
    val job1 = CompletableDeferred<T1>()
    val job2 = CompletableDeferred<T2>()

    execute { job1.complete(f1()) }
    execute { job2.complete(f2()) }

    Pair(job1.await(), job2.await())
}