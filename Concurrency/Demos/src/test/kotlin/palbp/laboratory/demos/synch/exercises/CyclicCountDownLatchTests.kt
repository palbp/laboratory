package palbp.laboratory.demos.synch.exercises

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CyclicCountDownLatchTests {

    @Test
    fun `countDownAndAwait with initial count of 0 fails with IllegalArgumentException`() {
        assertThrows<IllegalArgumentException>(message = "Expected IllegalArgumentException for initial count of 0") {
            CyclicCountDownLatch(initialCount = 0)
        }
    }

    @Test
    fun `countDownAndAwait with initial count of 1 should return true immediately`() {
        val latch = CyclicCountDownLatch(initialCount = 1)
        val result = latch.countDownAndAwait(timeout = Duration.ZERO)
        assert(value = result) { "Expected countDownAndAwait to return true" }
    }

    @Test
    fun `countDownAndAwait with initial count of 2 should return true after one count down`() {
        val latch = CyclicCountDownLatch(initialCount = 2)
        var resultOfFirst = false
        val first = Thread.ofPlatform().start {
            resultOfFirst = latch.countDownAndAwait(timeout = Duration.INFINITE)
        }

        val result = latch.countDownAndAwait(timeout = Duration.INFINITE)
        first.join()

        assert(value = result) { "Expected countDownAndAwait to return true after second count down" }
        assert(value = resultOfFirst) { "Expected first thread to return true after counter reaching zero" }
    }

    @Test
    fun `countDownAndAwait with initial count of 2 should return false after timeout without reaching zero`() {
        val latch = CyclicCountDownLatch(initialCount = 2)
        val result = latch.countDownAndAwait(timeout = 1.toDuration(unit = DurationUnit.SECONDS))
        assert(value = !result) { "Expected countDownAndAwait to return false due to timeout" }
    }
}