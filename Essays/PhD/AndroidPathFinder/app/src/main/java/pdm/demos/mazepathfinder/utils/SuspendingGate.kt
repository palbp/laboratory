package pdm.demos.mazepathfinder.utils

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

/**
 * A coroutine synchronization primitive. Its are as follows:
 * - It can be in one of two states: open or closed.
 * - When it is closed, calling [await] suspends the caller until [open] is called.
 * - When it is open, calling [await] does not suspend the caller.
 */
class SuspendingGate(private var isOpen: Boolean = true) {

    private val guard = Mutex(locked = false)
    private val waiting = mutableListOf<() -> Unit>()

    suspend fun await() {
        guard.lock()
        if (!isOpen) {
            suspendCancellableCoroutine { continuation ->
                waiting.add(element = { continuation.resume(Unit) })
                guard.unlock()
            }
        } else guard.unlock()
    }

    suspend fun open() = guard.withLock {
        if (isOpen) null
        else {
            isOpen = true
            waiting.toList().also { waiting.clear() }
        }
    }?.forEach {
        it()
    }

    suspend fun close() = guard.withLock { isOpen = false }
}