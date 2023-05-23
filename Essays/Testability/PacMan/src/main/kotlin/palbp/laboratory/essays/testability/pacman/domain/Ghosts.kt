package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents a ghost in the game arena.
 * @property info the ghost's identity
 * @property at the current coordinate of the ghost
 * @property facing the direction the ghost is facing, and therefore the direction in which it will move next.
 * @property previouslyAt the ghost's previous location in the maze. This is used to keep track of the ghost's movement.
 */
data class Ghost(
    val info: GhostInfo,
    val at: Coordinate,
    val facing: Direction,
    val previouslyAt: Coordinate = at
)

/**
 * Each ghost has its own identity. This enum associates each ghost with its starting position and direction.
 * The order of the values in this enum is important, as it's used to determine the ghost's color.
 */
enum class GhostInfo(val startsAt: Coordinate, val startsFacing: Direction) {
    SHADOW(startsAt = Coordinate(row = 11, column = 13), startsFacing = Direction.LEFT),
    SPEEDY(startsAt = Coordinate(row = 14, column = 13), startsFacing = Direction.DOWN),
    BASHFUL(startsAt = Coordinate(row = 14, column = 11), startsFacing = Direction.UP),
    POKEY(startsAt = Coordinate(row = 14, column = 15), startsFacing = Direction.UP)
}

/**
 * Moves the ghost to the next cell in the direction it's facing. If there's a wall in the way, the ghost changes
 * direction.
 */
fun Ghost.move(maze: Maze): Ghost {
    val nextFacing =
        if (maze.hasWall(at + facing)) {
            val possibleDirections = Direction.values().filter { it != facing && !maze.hasWall(at + it) }
            possibleDirections.random()
        } else facing

    return copy(at = at + nextFacing, facing = nextFacing, previouslyAt = at)
}
