package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.MAZE_HEIGHT
import palbp.laboratory.essays.testability.pacman.domain.MAZE_WIDTH
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.isMoving
import pt.isel.canvas.Canvas

const val ARENA_VIEW_WIDTH = MAZE_WIDTH * CELL_SIZE
const val ARENA_VIEW_HEIGHT = MAZE_HEIGHT * CELL_SIZE

/**
 * Draws the game arena on this canvas, clearing the previous content
 *
 * @param arena the game arena
 * @param step the movement step
 * @param heroAnimationStep the hero animation step
 */
fun Canvas.draw(arena: Arena, step: Step, heroAnimationStep: Step) {
    erase()
    draw(arena.maze)
    redraw(arena.pacMan, step, heroAnimationStep)
}

/**
 * Draws the game arena on this canvas, only updating the changed content
 *
 * @param arena the game arena
 * @param step the movement step
 * @param heroAnimationStep the hero animation step
 */
fun Canvas.redraw(arena: Arena, step: Step, heroAnimationStep: Step) {
    if (arena.pacMan.isMoving())
        redraw(arena.pacMan, step, heroAnimationStep)
}
