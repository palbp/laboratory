package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents the game's arena, which is composed of a maze and a hero.
 * @property maze the maze that composes the arena.
 * @property pacMan the hero that moves around the maze.
 */
data class Arena(val maze: Maze, val pacMan: Hero, val ghosts: List<Ghost>)

/**
 * Creates a new [Arena] instance in its initial state.
 */
fun createArena() = Arena(
    maze = createMaze(),
    pacMan = Hero(heroStartingPosition, Direction.RIGHT),
    ghosts = listOf(
        Ghost(id = GhostId.SHADOW, at = ghostsStartingPosition, facing = ghostsStartingFacing),
        Ghost(id = GhostId.POKEY, at = ghostsStartingPosition, facing = ghostsStartingFacing),
        Ghost(id = GhostId.BASHFUL, at = ghostsStartingPosition, facing = ghostsStartingFacing),
        Ghost(id = GhostId.SPEEDY, at = ghostsStartingPosition, facing = ghostsStartingFacing)
    )
)

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
 * Moves the ghosts in the arena, according to the maze's rules.
 */
fun ArenaState.moveGhosts(): ArenaState {
    val newGhosts = arena.ghosts.map { it.move(arena.maze) }
    return copy(arena = arena.copy(ghosts = newGhosts))
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
