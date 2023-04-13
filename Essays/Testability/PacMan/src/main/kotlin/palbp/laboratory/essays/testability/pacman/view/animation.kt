package palbp.laboratory.essays.testability.pacman.view

/**
 * Represents a step in an animation.
 * @param step the current step
 * @param totalSteps the total number of steps in the animation
 */
data class AnimationStep(val step: Int, val totalSteps: Int) {
    init {
        require(step >= 0 && totalSteps > 0 && step < totalSteps)
    }
}

/**
 * Returns true if this is the last step in the animation.
 */
fun AnimationStep.isLast() = step == totalSteps - 1

/**
 * Returns true if this is the first step in the animation.
 */
fun AnimationStep.isFirst() = step == 0

/**
 * Returns the next step in the animation.
 */
fun AnimationStep.next() = AnimationStep((step + 1) % totalSteps, totalSteps)
