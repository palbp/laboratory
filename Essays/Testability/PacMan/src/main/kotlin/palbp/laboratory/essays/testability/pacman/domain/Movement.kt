package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents movement direction.
 */
enum class Direction { UP, DOWN, LEFT, RIGHT }

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
 * Returns all coordinates that are adjacent to the current one.
 */
fun Coordinate.adjacent() = Direction.values().map { this + it }
