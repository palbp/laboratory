package palbp.laboratory.essays.testability.pacman.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class MovementTests {

    @Test
    fun `next step returns the next step`() {
        val sut = MovementStep(current = 0, totalSteps = 4)
        assertEquals(expected = MovementStep(current = 1, totalSteps = 4), actual = sut.next())
    }

    @Test
    fun `next step returns the first step when the current step is the last`() {
        val sut = MovementStep(current = 3, totalSteps = 4)
        assertEquals(expected = MovementStep(current = 0, totalSteps = 4), actual = sut.next())
    }
}
