package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.MAZE_HEIGHT
import palbp.laboratory.essays.testability.pacman.domain.MAZE_WIDTH
import palbp.laboratory.essays.testability.pacman.domain.MovementStep
import pt.isel.canvas.Canvas

const val ARENA_VIEW_WIDTH = MAZE_WIDTH * CELL_SIZE
const val ARENA_VIEW_HEIGHT = MAZE_HEIGHT * CELL_SIZE

/**
 * Draws the game arena on this canvas, clearing the previous content
 */
fun Canvas.draw(arena: Arena, step: MovementStep) {
    erase()
    drawLayout()
    redraw(arena.pacMan, step)
}

/**
 * Draws the game arena on this canvas, only updating the changed content
 */
fun Canvas.redraw(arena: Arena, step: MovementStep) {
    redraw(arena.pacMan, step)
}
