package palbp.laboratory.solutions.en2425

import palbp.laboratory.demos.coroutines.exercises.PeriodicMessage
import java.util.concurrent.Executor
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

suspend fun <T1,T2> Executor.invoke(f1: ()->T1, f2: ()->T2): Pair<T1,T2> {
    TODO()
}
