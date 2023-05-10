package palbp.laboratory.essays.testability.pacman.view

import org.junit.jupiter.api.Test
import palbp.laboratory.essays.testability.pacman.domain.Step
import kotlin.test.assertEquals

class AnimationTests {

    // To have realistic animations, the total number of steps should be a multiple of the scale
    private val totalSteps = (SCALE * 2).toInt()

    @Test
    fun `computeMovementStepDelta on first step returns a single delta from the starting point`() {
        val step = Step(current = 0, total = totalSteps)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = CELL_SIZE - CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeMovementStepDelta on second step returns a double delta from the starting point`() {
        val step = Step(current = 1, total = totalSteps)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = CELL_SIZE - 2 * CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeMovementStepDelta should return 0 when step is the last one`() {
        val step = Step(current = 3, total = totalSteps)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = 0, actual = result)
    }
}
