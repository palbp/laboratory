package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents movement direction.
 */
enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

/**
 * Creates a new coordinate by adding the received [direction] to the current one.
 * If the resulting coordinate is outside the maze, it wraps around to the other side.
 * @param direction the direction to add to the current coordinate.
 * @return a new coordinate.
 */
operator fun Coordinate.plus(direction: Direction) = when (direction) {
    Direction.UP -> Coordinate((row - 1 + MAZE_HEIGHT) % MAZE_HEIGHT, column)
    Direction.DOWN -> Coordinate((row + 1) % MAZE_HEIGHT, column)
    Direction.LEFT -> Coordinate(row, (column - 1 + MAZE_WIDTH) % MAZE_WIDTH)
    Direction.RIGHT -> Coordinate(row, (column + 1) % MAZE_WIDTH)
}

/**
 * The game's hero, Pac-Man
 * @property at the hero's current location in the maze.
 * @property facing the direction the hero is facing.
 */
data class Hero(val at: Coordinate, val facing: Direction)

/**
 * Gets a new hero instance at the same location but facing [to].
 */
fun Hero.face(to: Direction) = copy(facing = to)

/**
 * Moves the hero to the next cell in the direction he's facing. If the hero is facing a wall, he doesn't
 * move.
 */
fun Hero.move(maze: Maze): Hero {
    val nextCoordinate = at + facing
    return if (maze.hasWall(nextCoordinate)) this
    else copy(at = nextCoordinate)
}
