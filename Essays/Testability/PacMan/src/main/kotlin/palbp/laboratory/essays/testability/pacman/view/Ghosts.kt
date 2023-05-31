package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Ghost
import pt.isel.canvas.Canvas

/**
 * The number of frames per ghost move. The ghost moves one cell per move.
 */
const val FRAMES_PER_GHOST_MOVE = (SCALE * 3).toInt()

/**
 * Draws the given ghost in the canvas.
 */
fun Canvas.draw(ghost: Ghost, frameNumber: Int) {
    val spriteInfo = computeSpriteInfo(ghost, frameNumber)

    val scaledStepDelta = computeGhostMovementStepDelta(frameNumber)
    val (deltaX, deltaY) = when (ghost.facing) {
        Direction.RIGHT -> Pair(-scaledStepDelta, 0)
        Direction.LEFT -> Pair(scaledStepDelta, 0)
        Direction.UP -> Pair(0, scaledStepDelta)
        Direction.DOWN -> Pair(0, -scaledStepDelta)
    }

    val positionInArena = Point(
        x = Integer.max(ghost.at.column * CELL_SIZE - actorsOffset.x + deltaX, 0),
        y = Integer.max(ghost.at.row * CELL_SIZE - actorsOffset.y + deltaY, 0)
    )

    drawActorSprite(this, spriteInfo, positionInArena)
}

/**
 * Clears the given ghost from the canvas. This is done by clearing the area where the ghost was previously drawn.
 */
fun Canvas.clear(ghost: Ghost) {

    ghost.previouslyAt?.let {
        val previousPositionInArena = Point(
            x = Integer.max(ghost.previouslyAt.column * CELL_SIZE - actorsOffset.x, 0),
            y = Integer.max(ghost.previouslyAt.row * CELL_SIZE - actorsOffset.y, 0)
        )

        clearActorArea(previousPositionInArena)
    }
}

/**
 * Computes the location of the ghost's sprite on the actor's sprite sheet (see resources/sprites/actors.png).
 */
internal fun computeSpriteInfo(ghost: Ghost, frameNumber: Int): SpriteInfo {
    val ghostsInitialSpriteRow = 4
    val spriteSheetRow = ghostsInitialSpriteRow + ghost.id.ordinal

    val spriteSheetColumn = when (ghost.facing) {
        Direction.RIGHT -> 0
        Direction.LEFT -> 2
        Direction.UP -> 4
        Direction.DOWN -> 6
    }

    return SpriteInfo(spriteSheetRow, spriteSheetColumn)
}

internal fun computeGhostMovementStepDelta(frameNumber: Int): Int {
    val totalSteps = FRAMES_PER_GHOST_MOVE / 2
    val stepSize = CELL_SIZE / totalSteps
    val currentStep = (frameNumber % FRAMES_PER_GHOST_MOVE) / 2
    return if (currentStep == totalSteps - 1) 0
    else (CELL_SIZE - (currentStep + 1) * stepSize)
}
