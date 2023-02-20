package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.ARENA_HEIGHT
import palbp.laboratory.essays.testability.pacman.domain.ARENA_WIDTH
import palbp.laboratory.essays.testability.pacman.domain.Arena
import pt.isel.canvas.Canvas

const val ARENA_VIEW_WIDTH = ARENA_WIDTH * CELL_SIZE
const val ARENA_VIEW_HEIGHT = ARENA_HEIGHT * CELL_SIZE

/**
 * Draws the game arena on the received canvas
 */
fun drawArena(arena: Arena, canvas: Canvas) {
    //drawLayout(canvas)
    drawHero(canvas, arena.pacMan)
}

