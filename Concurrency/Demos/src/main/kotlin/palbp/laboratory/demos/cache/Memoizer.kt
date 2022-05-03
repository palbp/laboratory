package palbp.laboratory.demos.cache

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

typealias Computation<A, R> = (A) -> R

enum class MemoizerType {
    NON_SCALABLE,
    FOR_FUN_1,
    FOR_FUN_2,
    FOR_FUN_3,
    BEST_IN_CLASS
}

interface Cache<A, R> {
    fun get(arg: A): R
}

/**
 * Function that returns a memoized version of the received function. The memoized version returns previously computed
 * values without computing them again.
 * @param computation   the function whose results are to be memoized
 * @return a function that memorizes the results of previous calls to [computation] and returns the corresponding
 * value without the need to recompute the value again.
 */
fun <A, R> memoize(type: MemoizerType, computation: Computation<A, R>): Computation<A, R> {
    val memoizer: Cache<A, R> = when (type) {
        MemoizerType.NON_SCALABLE -> NonScalableCache(computation)
        MemoizerType.BEST_IN_CLASS -> ScalableCache(computation)
        MemoizerType.FOR_FUN_1 -> MyCustomCache1(computation)
        MemoizerType.FOR_FUN_2 -> MyCustomCache2(computation)
        MemoizerType.FOR_FUN_3 -> MyCustomCache3(computation)
    }
    return {
        memoizer.get(it)
    }
}

/**
 * A poor implementation of a cache. =-/
 * The implementation is thread-safe, but it does not scale adequately for contended accesses.
 */
private class NonScalableCache<A, R>(private val computation: Computation<A, R>): Cache<A, R> {

    private val cachedResults = mutableMapOf<A, R>()
    private val lock = ReentrantLock()

    override fun get(arg: A): R = lock.withLock {
        cachedResults[arg] ?: computation(arg).also {
            cachedResults[arg] = it
        }
    }
}

/**
 * A best in class implementation of a cache that makes use of an existing construct.
 */
private class ScalableCache<A, R>(private val computation: Computation<A, R>): Cache<A, R> {
    private val cachedResults = ConcurrentHashMap<A, R>()
    override fun get(arg: A): R = cachedResults.computeIfAbsent(arg, computation)
}

/**
 * A custom cache implementation, just for fun. How good can we make it?
 */
private class MyCustomCache1<A, R>(private val computation: Computation<A, R>): Cache<A, R> {

    private class CacheFuture<A, R>(private val computation: Computation<A, R>) {

        private val lock = ReentrantLock()
        private var result: R? = null

        fun get(arg: A): R = lock.withLock {
            (result ?: computation(arg)).also { result = it }
        }
    }

    private val lock = ReentrantLock()
    private val cachedResults = mutableMapOf<A, CacheFuture<A, R>>()

    override fun get(arg: A): R {
        val result = lock.withLock {
            cachedResults[arg] ?: CacheFuture(computation).also { cachedResults[arg] = it }
        }
        return result.get(arg)
    }
}

/**
 * A custom cache implementation, just for fun. How good can we make it?
 */
private class MyCustomCache2<A, R>(private val computation: Computation<A, R>): Cache<A, R> {

    private class CacheFuture<A, R>(private val computation: Computation<A, R>) {

        private val lock = ReentrantLock()
        private @Volatile var result: R? = null

        fun get(arg: A): R {
            if (result != null) {
                return result as R
            }

            return lock.withLock {
                result = computation(arg)
                result as R
            }
        }
    }

    private val lock = ReentrantLock()
    private val cachedResults = mutableMapOf<A, CacheFuture<A, R>>()

    override fun get(arg: A): R {
        val result = lock.withLock {
            cachedResults[arg] ?: CacheFuture(computation).also { cachedResults[arg] = it }
        }
        return result.get(arg)
    }
}


/**
 * A custom cache implementation, just for fun. How good can we make it?
 */
private class MyCustomCache3<A, R>(private val computation: Computation<A, R>): Cache<A, R> {

    private class CacheFuture<A, R>(private val computation: Computation<A, R>) {

        private val lock = ReentrantLock()
        private @Volatile var result: R? = null

        fun get(arg: A): R {
            if (result != null) {
                return result as R
            }

            return lock.withLock {
                if (result == null)
                    result = computation(arg)

                result as R
            }
        }
    }

    private val lock = ReentrantLock()
    private val cachedResults = mutableMapOf<A, CacheFuture<A, R>>()

    override fun get(arg: A): R {
        val a = AtomicLong(0)
        val result = lock.withLock {
            cachedResults[arg] ?: CacheFuture(computation).also { cachedResults[arg] = it }
        }
        return result.get(arg)
    }
}
