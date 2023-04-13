package palbp.laboratory.essays.testability.pacman.view

import org.junit.jupiter.api.Test
import palbp.laboratory.essays.testability.pacman.domain.MovementStep
import kotlin.test.assertEquals

class AnimationTests {

    @Test
    fun `computeAnimationDelta on first step returns a single delta from the starting point`() {
        val totalSteps = 3
        val step = MovementStep(current = 0, totalSteps = totalSteps)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = CELL_SIZE - CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeAnimationDelta on second step returns a double delta from the starting point`() {
        val totalSteps = 3
        val step = MovementStep(current = 1, totalSteps = totalSteps)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = CELL_SIZE - 2 * CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeMovementStepDelta should return 0 when step is the last one`() {
        val step = MovementStep(current = 2, totalSteps = 3)
        val result = computeMovementStepDelta(step)

        assertEquals(expected = 0, actual = result)
    }
}