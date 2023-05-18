package palbp.laboratory.essays.testability.pacman.domain

import palbp.laboratory.essays.testability.pacman.domain.GhostsMode.CHASE
import palbp.laboratory.essays.testability.pacman.domain.GhostsMode.SCATTER

/**
 * The hero's starting position in the maze.
 * Whenever Pac-Man spawns, he does it at the exact same location.
 */
val pacManStartingPosition = Coordinate(row = 23, column = 13)

/**
 * Represents the game's arena, which is composed of a maze and a hero.
 * @property maze the maze that composes the arena.
 * @property pacMan the hero that moves around the maze.
 */
data class Arena(val maze: Maze, val pacMan: Hero)

/**
 * Creates a new [Arena] instance in its initial state.
 */
fun createArena() = Arena(createMaze(), Hero(pacManStartingPosition, Direction.RIGHT))

/**
 * The mode the ghosts are in. This affects their behavior. When in [CHASE] mode, they chase the hero. When in
 * [SCATTER] mode, they run away from the hero and can be eaten. Note that this is the general behaviour of the
 * ghosts, but each ghost has its own specific behaviour. For example, if a ghost has already been eaten, it no longer
 * runs away from the hero, even if the arena is in [SCATTER] mode.
 */
enum class GhostsMode { CHASE, SCATTER }

/**
 * Represents the arena state, which is composed of the arena contents and the last action performed by the hero.
 */
data class ArenaState(val arena: Arena, val action: HeroAction)

/**
 * Moves the hero in the arena, according to the maze's rules.
 * @return the new arena state, after moving the actors.
 */
fun ArenaState.moveHero(): ArenaState {
    val heroMovementResult = arena.pacMan.move(arena.maze)

    val newArenaState =
        if (heroMovementResult.action == HeroAction.EAT_PELLET || heroMovementResult.action == HeroAction.EAT_POWER_PELLET) {
            val newArena = arena.copy(maze = arena.maze.removePellet(heroMovementResult.hero.at), pacMan = heroMovementResult.hero)
            if (heroMovementResult.action == HeroAction.EAT_POWER_PELLET)
                copy(arena = newArena, action = heroMovementResult.action)
            else copy(arena = newArena, action = heroMovementResult.action)
        } else
            copy(arena = arena.copy(pacMan = heroMovementResult.hero), action = heroMovementResult.action)

    return newArenaState
}

/**
 * Changes the hero's intended movement direction.
 * @param to the new intended direction.
 * @return a new [Arena] instance with the hero intending to move to the new direction.
 */
fun ArenaState.changeHeroDirection(to: Direction): ArenaState {
    val nextPacMan = arena.pacMan.changeIntent(to)
    return copy(arena = arena.copy(pacMan = nextPacMan))
}

/**
 * Checks if the hero is moving.
 */
fun ArenaState.isHeroMoving(): Boolean = arena.pacMan.isMoving()
