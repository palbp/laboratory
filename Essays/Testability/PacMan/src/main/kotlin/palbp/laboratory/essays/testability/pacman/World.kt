package palbp.laboratory.essays.testability.pacman

import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.changeHeroDirection
import palbp.laboratory.essays.testability.pacman.domain.createArena
import palbp.laboratory.essays.testability.pacman.domain.moveHero
import palbp.laboratory.essays.testability.pacman.domain.next
import palbp.laboratory.essays.testability.pacman.domain.isFirst
import pt.isel.canvas.Canvas

import palbp.laboratory.essays.testability.pacman.view.draw
import palbp.laboratory.essays.testability.pacman.view.redraw

/**
 * Represents the game's world, which is composed of an arena and a movement step, used to determine when the hero
 * should move in the arena.
 *
 * @param arena the game arena
 * @param movementStep the movement step
 */
data class World(val arena: Arena = createArena(), val movementStep: Step)

/**
 * Produces the next world state, by moving the hero in the arena, if appropriate.
 */
fun World.doStep(): World {
    val nextStep = movementStep.next()
    val nextArena = if (nextStep.isFirst()) arena.moveHero() else arena
    return World(nextArena, nextStep)
}

/**
 * Changes the hero's intended movement direction.
 */
fun World.changeHeroDirection(direction: Direction) = copy(arena = arena.changeHeroDirection(direction))

/**
 * Draws the game arena on this canvas, clearing the previous content
 */
fun Canvas.draw(world: World) {
    draw(world.arena, world.movementStep)
}

/**
 * Draws the game arena on this canvas, only updating the changed content
 */
fun Canvas.redraw(world: World) {
    redraw(world.arena, world.movementStep)
}