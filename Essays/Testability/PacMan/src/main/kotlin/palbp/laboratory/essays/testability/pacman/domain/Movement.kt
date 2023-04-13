package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents a step in the actor's movement.
 *
 * The actor speed is determined by the number of steps in the movement. The larger the number of steps, the slower the
 * actor moves.
 *
 * @param current the current step
 * @param totalSteps the total number of steps in the movement
 */
data class MovementStep(val current: Int, val totalSteps: Int)

/**
 * Returns the next step in the movement.
 */
fun MovementStep.next() = MovementStep((current + 1) % totalSteps, totalSteps)

/**
 * Returns true if this is the first step in the movement, and therefore the actor should move.
 */
fun MovementStep.shouldMove() = current == 0
