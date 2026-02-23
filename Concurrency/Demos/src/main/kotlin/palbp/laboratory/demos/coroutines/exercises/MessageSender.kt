package palbp.laboratory.demos.coroutines.exercises

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MessageSender<T> {

    private val guard = Mutex()
    private var waiting = mutableListOf<Continuation<T>>()

    suspend fun waitForMessage(message: T) {
        guard.lock()
        suspendCoroutine { continuation ->
            waiting.add(continuation)
            guard.unlock()
        }
    }

    suspend fun sendToAll(message: T): Int {
        val wereWaiting = guard.withLock {
            val snapshot = waiting
            waiting = mutableListOf()
            snapshot
        }

        wereWaiting.forEach { continuation -> continuation.resume(message) }
        return wereWaiting.size
    }
}