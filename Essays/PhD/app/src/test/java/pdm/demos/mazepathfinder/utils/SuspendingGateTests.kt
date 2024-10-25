package pdm.demos.mazepathfinder.utils

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Test

class SuspendingGateTests {

    @Test
    fun `await on open gate returns immediately`() = runBlocking {
        val gate = SuspendingGate()
        async { gate.await() }.await()
    }

    @Test(expected = TimeoutCancellationException::class)
    fun `await on closed gate suspends caller`(): Unit = runBlocking {
        val gate = SuspendingGate(isOpen = false)

        withTimeout(1000) {
            async {
                gate.await()
            }.await()
        }
    }

    @Test
    fun `await on closed gate suspends until gate is opened`() = runBlocking {
        val sut = SuspendingGate(isOpen = false)

        val waiter = async(start = CoroutineStart.UNDISPATCHED) {
            sut.await()
        }
        sut.open()

        withTimeout(1000) { waiter.await() }
        assert(waiter.isCompleted) { "The waiting coroutine should have completed" }
    }

    @Test
    fun `all waiters are resumed when gate is opened`() = runBlocking {
        val sut = SuspendingGate(isOpen = false)

        val waiters = List(10) {
            async(start = CoroutineStart.UNDISPATCHED) {
                sut.await()
            }
        }

        sut.open()

        withTimeout(1000) {
            waiters.forEach { it.await() }
        }
        assert(waiters.all { it.isCompleted }) { "All waiting coroutines should have completed" }
    }
}