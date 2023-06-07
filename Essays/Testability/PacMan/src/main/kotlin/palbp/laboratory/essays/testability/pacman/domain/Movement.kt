package palbp.laboratory.essays.testability.pacman.domain

import kotlin.math.abs

/**
 * Represents movement direction.
 */
enum class Direction { UP, DOWN, LEFT, RIGHT }

/**
 * Returns the opposite direction to the current one.
 */
fun Direction.opposite() = when (this) {
    Direction.UP -> Direction.DOWN
    Direction.DOWN -> Direction.UP
    Direction.LEFT -> Direction.RIGHT
    Direction.RIGHT -> Direction.LEFT
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
 * Returns all coordinates that are adjacent to the current one.
 */
fun Coordinate.adjacent() = Direction.values().map { this + it }

/**
 * Returns the distance between the current coordinate and the given one.
 */
fun Coordinate.distanceTo(other: Coordinate) = abs(row - other.row) + abs(column - other.column)

/**
 * Returns the direction from the current coordinate to the given one.
 */
fun Coordinate.directionTo(other: Coordinate) = when {
    row < other.row -> Direction.DOWN
    row > other.row -> Direction.UP
    column < other.column -> Direction.RIGHT
    else -> Direction.LEFT
}
