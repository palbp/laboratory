package palbp.laboratory.demos.coroutines.exercises

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.CompletableFuture
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
/**
 * A possible solution for the following exercise.
 *
 * Consider the Exchanger synchronizer performed in the first set of exercises. Make a synchronizer with similar
 * functionality, but in which the exchange function is a suspending function, that is, it does not block the invoking
 * thread while waiting. For simplification, the function does not support timeout or cancellation.
 *
 * class Exchanger<T> {
 *    suspend fun exchange(value: T): T { … }
 * }
 *
 * This synchronizer supports the exchange of information between pairs of coroutines. Coroutines that use it,
 * manifest their availability to initiate an exchange by invoking the exchange method, specifying the object they
 * want to deliver to the partner coroutine (value). The exchange method ends up returning the exchanged value,
 * when the exchange is performed with another coroutine.
 */
@Suppress("Unused")
class SuspendingExchangerAsPerMyClasses<T> {

    private data class Request<T>(val value: T, val result: CompletableFuture<T>)

    private val guard = ReentrantLock()
    private var waiting: Request<T>? = null

    private fun internalExchange(value: T): CompletableFuture<T> {
        val myLazyRequest = lazy { Request(value, CompletableFuture()) }
        val waitingRequest = guard.withLock {
            val other = waiting
            if (other == null) { waiting = myLazyRequest.value; null }
            else { waiting = null; other}
        }

        return if (waitingRequest != null) {
            val exchangedValue = waitingRequest.value
            waitingRequest.result.complete(value)
            CompletableFuture.completedFuture(exchangedValue)
        }
        else {
            myLazyRequest.value.result
        }
    }

    suspend fun exchange(value: T): T {
        return suspendCoroutine { continuation ->
            internalExchange(value).whenComplete { value, error ->
                if (error != null)
                    continuation.resumeWithException(error)
                else
                    continuation.resume(value)
            }
        }
    }
}

class SuspendingExchange<T> {

    private data class Request<T>(val value: T, val continuation: Continuation<T>)

    private val guard = Mutex()
    private var waiting: Request<T>? = null

    suspend fun exchange(value: T): T {
        TODO()
    }
}