package palbp.laboratory.essays.testability.pacman.view

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AnimationTests {

    // To have realistic animations, the total number of steps should be a multiple of the scale
    private val totalSteps = FRAMES_PER_HERO_MOVE

    @Test
    fun `computeMovementStepDelta on first step returns a single delta from the starting point`() {
        val result = computeMovementStepDelta(frameNumber = 0)
        assertEquals(expected = CELL_SIZE - CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeMovementStepDelta on second step returns a double delta from the starting point`() {
        val result = computeMovementStepDelta(frameNumber = 1)
        assertEquals(expected = CELL_SIZE - 2 * CELL_SIZE / totalSteps, actual = result)
    }

    @Test
    fun `computeMovementStepDelta should return 0 when step is the last one`() {
        val result = computeMovementStepDelta(frameNumber = 3)
        assertEquals(expected = 0, actual = result)
    }
}
