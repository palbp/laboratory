package palbp.laboratory.essays.testability.pacman.domain

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
 * Moves the hero in the arena, according to the maze's rules.
 * @return the new arena state, after moving the actors.
 */
fun Arena.moveHero(): ArenaState {
    val heroMovementResult = pacMan.move(maze)

    val newArena =
        if (heroMovementResult.action == HeroAction.EAT_PELLET || heroMovementResult.action == HeroAction.EAT_POWER_PELLET)
            copy(maze = maze.clearCell(heroMovementResult.hero.at), pacMan = heroMovementResult.hero)
        else copy(pacMan = heroMovementResult.hero)

    return ArenaState(newArena, heroMovementResult.action)
}

/**
 * Changes the hero's intended movement direction.
 * @param to the new intended direction.
 * @return a new [Arena] instance with the hero intending to move to the new direction.
 */
fun Arena.changeHeroDirection(to: Direction): Arena {
    val nextPacMan = pacMan.changeIntent(to)
    return copy(pacMan = nextPacMan)
}

/**
 * Represents the arena state, which is composed of the arena contents and the last action performed by the hero.
 */
data class ArenaState(val arena: Arena, val action: HeroAction)
