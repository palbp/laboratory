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
 * Moves the hero to the next cell in the direction he's facing. If the hero is facing a wall, he doesn't move.
 * @return a new [Arena] instance with the hero moved, if possible.
 */
fun Arena.moveHero(): Arena {
    val nextPacMan = pacMan.move(maze)
    return if (nextPacMan == pacMan) this
    else copy(pacMan = nextPacMan)
}

/**
 * Changes the hero's facing direction.
 * @param to the new facing direction.
 * @return a new [Arena] instance with the hero facing the new direction.
 */
fun Arena.changeHeroDirection(to: Direction): Arena {
    val nextPacMan = pacMan.face(to)
    return copy(pacMan = nextPacMan)
}
