package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents the game's world, which is composed of an arena and a movement step, used to determine when the actors
 * should move in the arena.
 */
data class World(val arena: Arena = createArena(), val step: MovementStep = MovementStep(current = 0, totalSteps = 4))

/**
 * Produces the next world state, by moving the actors in the arena, if appropriate.
 */
fun World.doStep(): World {
    val nextStep = step.next()
    val nextArena = if (nextStep.shouldMove()) arena.moveHero() else arena
    return World(nextArena, nextStep)
}

/**
 * Changes the hero's intended movement direction.
 */
fun World.changeHeroDirection(direction: Direction) = copy(arena = arena.changeHeroDirection(direction))
