package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.World
import pt.isel.canvas.Canvas

/**
 * Redraws the game world on this canvas, only updating the changed content
 */
fun Canvas.redraw(world: World) {
    redraw(world.arena, world.step)
}

/**
 * Draws the game world on this canvas, clearing the previous content
 */
fun Canvas.draw(world: World) {
    draw(world.arena, world.step)
}
