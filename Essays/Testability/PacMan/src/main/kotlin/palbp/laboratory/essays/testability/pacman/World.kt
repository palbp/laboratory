package palbp.laboratory.essays.testability.pacman

import palbp.laboratory.essays.testability.pacman.domain.ArenaState
import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.HeroAction
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.changeHeroDirection
import palbp.laboratory.essays.testability.pacman.domain.createArena
import palbp.laboratory.essays.testability.pacman.domain.isFirst
import palbp.laboratory.essays.testability.pacman.domain.isMoving
import palbp.laboratory.essays.testability.pacman.domain.moveHero
import palbp.laboratory.essays.testability.pacman.domain.next
import palbp.laboratory.essays.testability.pacman.view.ANIMATION_STEP_COUNT
import palbp.laboratory.essays.testability.pacman.view.SCALE
import palbp.laboratory.essays.testability.pacman.view.draw
import palbp.laboratory.essays.testability.pacman.view.redraw
import pt.isel.canvas.Canvas
import pt.isel.canvas.playSound

/**
 * Represents the game's world, which is composed of an arena and a movement step, used to determine when the hero
 * should move in the arena.
 *
 * @param arenaState the current state of the game arena
 * @param movementStep the movement step
 * @param heroAnimationStep the hero animation step
 */
data class World(
    val arenaState: ArenaState = ArenaState(createArena(), HeroAction.MOVE),
    val movementStep: Step = Step(current = 0, total = SCALE.toInt()),
    val heroAnimationStep: Step = Step(current = 0, total = ANIMATION_STEP_COUNT)
)

/**
 * Produces the next world state, by moving the hero in the arena.
 */
fun World.doStep(): World {
    val nextStep = movementStep.next()
    val nextArenaState = if (nextStep.isFirst()) arenaState.arena.moveHero() else arenaState

    if (arenaState.action != HeroAction.EAT_PELLET && nextArenaState.action == HeroAction.EAT_PELLET) {
        playSoundLoop("sounds/munch")
    } else if (arenaState.action == HeroAction.EAT_PELLET && nextArenaState.action != HeroAction.EAT_PELLET) {
        stopSoundLoop("sounds/munch")
    }

    return World(
        arenaState = nextArenaState,
        movementStep = nextStep,
        heroAnimationStep = if (arenaState.arena.pacMan.isMoving()) heroAnimationStep.next() else heroAnimationStep
    )
}

/**
 * Changes the hero's intended movement direction.
 */
fun World.changeHeroDirection(direction: Direction) =
    copy(arenaState = arenaState.copy(arena = arenaState.arena.changeHeroDirection(direction)))

/**
 * Draws the game world on this canvas
 */
fun Canvas.draw(world: World) = draw(world.arenaState.arena, world.movementStep, world.heroAnimationStep)

/**
 * Draws the game world on this canvas, only updating the changed content
 */
fun Canvas.redraw(world: World) = redraw(world.arenaState.arena, world.movementStep, world.heroAnimationStep)