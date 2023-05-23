package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Ghost
import pt.isel.canvas.Canvas

/**
 * Draws the given ghost in the canvas.
 */
fun Canvas.redraw(ghost: Ghost, frameNumber: Int) {
    val spriteInfo = computeSpriteInfo(ghost, frameNumber)

    val scaledStepDelta = computeGhostMovementStepDelta(frameNumber)
//    val (deltaX, deltaY) = when (ghost.facing) {
//        Direction.RIGHT -> Pair(-scaledStepDelta, 0)
//        Direction.LEFT -> Pair(scaledStepDelta, 0)
//        Direction.UP -> Pair(0, scaledStepDelta)
//        Direction.DOWN -> Pair(0, -scaledStepDelta)
//    }

    val (deltaX, deltaY) = Pair(scaledStepDelta, 0)

    val positionInArena = Point(
        x = Integer.max(ghost.at.column * CELL_SIZE - actorsOffset.x + deltaX, 0),
        y = Integer.max(ghost.at.row * CELL_SIZE - actorsOffset.y + deltaY, 0)
    )

//    val previousPositionInArena = Point(
//        x = Integer.max(ghost.previouslyAt.column * CELL_SIZE - actorsOffset.x, 0),
//        y = Integer.max(ghost.previouslyAt.row * CELL_SIZE - actorsOffset.y, 0)
//    )
//
//    clearActorArea(previousPositionInArena)
    clearActorArea(positionInArena)
    drawActorSprite(this, spriteInfo, positionInArena)
}

/**
 * Computes the location of the ghost's sprite on the actor's sprite sheet (see resources/sprites/actors.png).
 */
internal fun computeSpriteInfo(ghost: Ghost, frameNumber: Int): SpriteInfo {
    val ghostsInitialSpriteRow = 4
    val spriteSheetRow = ghostsInitialSpriteRow + ghost.info.ordinal

    val spriteSheetColumn = when (ghost.facing) {
        Direction.RIGHT -> 0
        Direction.LEFT -> 2
        Direction.UP -> 4
        Direction.DOWN -> 6
    }

    return SpriteInfo(spriteSheetRow, spriteSheetColumn)
}

/**
 * Computes the scaled step delta (used to determine the ghost's position on the canvas). Returns the variation of the
 * step, in pixels, that should be applied to the actor's position. Note that the actor has already actually moved
 * (its hit box has already changed), but the animation is still in progress, so the actor is not yet in its final
 * position on the screen. This is only accomplished in the last step of the animation.
 */
internal fun computeGhostMovementStepDelta(frameNumber: Int): Int {
    return CELL_SIZE / 2
}
