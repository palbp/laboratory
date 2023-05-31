package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.FPS
import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.Coordinate
import palbp.laboratory.essays.testability.pacman.domain.MAZE_HEIGHT
import palbp.laboratory.essays.testability.pacman.domain.MAZE_WIDTH
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.hasPellet
import pt.isel.canvas.Canvas

const val ARENA_VIEW_WIDTH = MAZE_WIDTH * CELL_SIZE
const val ARENA_VIEW_HEIGHT = MAZE_HEIGHT * CELL_SIZE

/**
 * Draws the game arena on this canvas, clearing the previous content
 *
 * @param arena the game arena
 * @param frameNumber the current frame number
 * @param heroAnimationStep the hero animation step
 */
fun Canvas.draw(arena: Arena, frameNumber: Int, heroAnimationStep: Step) {
    erase()
    draw(arena.maze)
    redraw(arena.pacMan, frameNumber, heroAnimationStep)
}

/**
 * Draws the game arena on this canvas, only updating the changed content
 *
 * @param arena the game arena
 * @param frameNumber the current frame number
 * @param heroAnimationStep the hero animation step
 */
fun Canvas.redraw(arena: Arena, frameNumber: Int, heroAnimationStep: Step) {

    arena.ghosts.forEach { clear(it) }
    redrawPellets(arena)
    redraw(arena.pacMan, frameNumber, heroAnimationStep)
    arena.ghosts.forEach { draw(it, frameNumber) }
    redrawPowerPellets(arena, frameNumber, arena.maze.powerPelletsLocations)
}

/**
 * Redraws the power pellets on this canvas, making them blink according to the current frame number
 */
private fun Canvas.redrawPowerPellets(arena: Arena, frameNumber: Int, powerPelletsLocations: List<Coordinate>) {

    val ghostsLocations = arena.ghosts.flatMap { listOf(it.at, it.previouslyAt) }

    powerPelletsLocations
        .filter { !ghostsLocations.contains(it) }
        .forEach {
            if (frameNumber % FPS == 0) drawPowerPellet(it.toPoint())
            else if (frameNumber % (FPS / 2) == 0) eraseCell(it.toPoint())
        }
}

/**
 * Redraws the pellets that were being hidden by the ghosts
 */
private fun Canvas.redrawPellets(arena: Arena) {
    arena.ghosts.mapNotNull { it.previouslyAt }
        .filter { arena.maze.hasPellet(it) }
        .forEach { drawPellet(it.toPoint()) }
}
