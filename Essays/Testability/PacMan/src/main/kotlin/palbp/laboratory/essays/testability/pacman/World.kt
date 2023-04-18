package palbp.laboratory.essays.testability.pacman

import palbp.laboratory.essays.testability.pacman.domain.Arena
import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.changeHeroDirection
import palbp.laboratory.essays.testability.pacman.domain.createArena
import palbp.laboratory.essays.testability.pacman.domain.isFirst
import palbp.laboratory.essays.testability.pacman.domain.moveHero
import palbp.laboratory.essays.testability.pacman.domain.next

/**
 * Represents the game's world, which is composed of an arena and a movement step, used to determine when the hero
 * should move in the arena.
 *
 * @param arena the game arena
 * @param movementStep the movement step
 * @param heroAnimationStep the hero animation step
 */
data class World(
    val arena: Arena = createArena(),
    val movementStep: Step,
    val heroAnimationStep: Step
)

/**
 * Produces the next world state, by moving the hero in the arena.
 */
fun World.doStep(): World {
    val nextStep = movementStep.next()
    val nextArena = if (nextStep.isFirst()) arena.moveHero() else arena
    return World(nextArena, nextStep, heroAnimationStep.next())
}

/**
 * Changes the hero's intended movement direction.
 */
fun World.changeHeroDirection(direction: Direction) = copy(arena = arena.changeHeroDirection(direction))
