package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.MAZE_HEIGHT
import palbp.laboratory.essays.testability.pacman.domain.MAZE_WIDTH
import pt.isel.canvas.Canvas

const val ARENA_VIEW_WIDTH = MAZE_WIDTH * CELL_SIZE
const val ARENA_VIEW_HEIGHT = MAZE_HEIGHT * CELL_SIZE

/**
 * Draws the game arena on the received canvas
 */
fun drawArena(arena: Arena, canvas: Canvas) {
    clearArena(canvas)
    drawLayout(canvas)
    drawHero(arena.pacMan, canvas)
}

/**
 * Clears the arena.
 */
fun clearArena(canvas: Canvas) {
    canvas.erase()
}
