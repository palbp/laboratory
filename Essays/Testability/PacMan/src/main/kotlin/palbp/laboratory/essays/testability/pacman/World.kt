package palbp.laboratory.essays.testability.pacman

import palbp.laboratory.essays.testability.pacman.domain.ArenaState
import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.HeroAction
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.domain.changeHeroDirection
import palbp.laboratory.essays.testability.pacman.domain.createArena
import palbp.laboratory.essays.testability.pacman.domain.isHeroMoving
import palbp.laboratory.essays.testability.pacman.domain.moveGhosts
import palbp.laboratory.essays.testability.pacman.domain.moveHero
import palbp.laboratory.essays.testability.pacman.domain.next
import palbp.laboratory.essays.testability.pacman.view.ANIMATION_STEP_COUNT
import palbp.laboratory.essays.testability.pacman.view.FRAMES_PER_GHOST_MOVE
import palbp.laboratory.essays.testability.pacman.view.FRAMES_PER_HERO_MOVE
import palbp.laboratory.essays.testability.pacman.view.draw
import palbp.laboratory.essays.testability.pacman.view.redraw
import pt.isel.canvas.Canvas

/**
 * The identifier of the wav resource containing the munch sound effect
 */
const val MUNCH_SOUND = "sounds/munch"

/**
 * The identifier of the wav resource containing the siren sound effect
 */
const val SIREN_SOUND = "sounds/siren"

/**
 * The identifier of the wav resource containing the sound effect used after a power pellet is eaten. This sound
 * effect replaces the siren sound effect while the ghosts are vulnerable.
 */
const val POWER_PELLET_SOUND = "sounds/power_pellet"

/**
 * Frames per second
 */
const val FPS = 40

/**
 * The duration of the scatter mode. The game enters scatter mode when a power pellet is eaten.
 */
const val SCATTER_MODE_DURATION = 8 * FPS

/**
 * Represents the game's world, which is composed of an arena and a movement step, used to determine when the hero
 * should move in the arena.
 *
 * @param arenaState the current state of the game arena
 * @param heroAnimationStep the hero animation step
 * @param frameNumber the current frame number. Its value is incremented on each frame, as long as the game is running.
 * @param scatterModeEnd the frame number when the scatter mode should end. The game enters scatter mode when a power
 * pellet is eaten. This property is used to determine when the game should exit scatter mode.
 */
data class World(
    val arenaState: ArenaState = ArenaState(createArena(), HeroAction.MOVE),
    val heroAnimationStep: Step = Step(current = 0, total = ANIMATION_STEP_COUNT),
    val frameNumber: Int = -1,
    val scatterModeEnd: Int? = null,
)

/**
 * Produces the next world state, by moving the hero in the arena and enforcing its consequences.
 */
fun World.doStep(): World {

    val nextFrameNumber = frameNumber + 1

    val arenaAfterGhostsMove = if (nextFrameNumber % FRAMES_PER_GHOST_MOVE == 0) arenaState.moveGhosts() else arenaState
    val nextArenaState = if (nextFrameNumber % FRAMES_PER_HERO_MOVE == 0) arenaAfterGhostsMove.moveHero() else arenaAfterGhostsMove

    val nextScatterModeEnd = when {
        nextArenaState.action == HeroAction.EAT_POWER_PELLET -> frameNumber + SCATTER_MODE_DURATION
        scatterModeEnd == frameNumber -> null
        else -> scatterModeEnd
    }

    val nextWorld = World(
        arenaState = nextArenaState,
        heroAnimationStep = if (arenaState.isHeroMoving()) heroAnimationStep.next() else heroAnimationStep,
        frameNumber = nextFrameNumber,
        scatterModeEnd = nextScatterModeEnd
    )

    computeSoundEffects(world = this, nextWorld = nextWorld)

    return nextWorld
}

/**
 * Computes the sound effects to be played for the changes in the world state.
 */
private fun computeSoundEffects(world: World, nextWorld: World) {

    val isEating = world.arenaState.action == HeroAction.EAT_PELLET || world.arenaState.action == HeroAction.EAT_POWER_PELLET
    val willBeEating = nextWorld.arenaState.action == HeroAction.EAT_PELLET || nextWorld.arenaState.action == HeroAction.EAT_POWER_PELLET

    val enteredScatterMode = world.scatterModeEnd == null && nextWorld.scatterModeEnd != null
    val exitedScatterMode = world.scatterModeEnd != null && nextWorld.scatterModeEnd == null

    when {
        !isEating && willBeEating -> playSoundLoop(MUNCH_SOUND)
        isEating && !willBeEating -> stopSoundLoop(MUNCH_SOUND)
        enteredScatterMode -> { stopSoundLoop(SIREN_SOUND); playSoundLoop(POWER_PELLET_SOUND) }
        exitedScatterMode -> { playSoundLoop(SIREN_SOUND); stopSoundLoop(POWER_PELLET_SOUND) }
    }
}

/**
 * Changes the hero's intended movement direction.
 */
fun World.changeHeroDirection(direction: Direction) =
    copy(arenaState = arenaState.changeHeroDirection(direction))

/**
 * Draws the game world on this canvas
 */
fun Canvas.draw(world: World) = draw(world.arenaState.arena, world.frameNumber, world.heroAnimationStep)

/**
 * Draws the game world on this canvas, only updating the changed content
 */
fun Canvas.redraw(world: World) = redraw(world.arenaState.arena, world.frameNumber, world.heroAnimationStep)
