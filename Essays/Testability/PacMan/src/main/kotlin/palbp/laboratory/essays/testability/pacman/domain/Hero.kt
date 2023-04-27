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
 * @property facing the direction the hero is facing
 * @property intent the direction the hero wants to move to. This is used to change the movement direction once
 * there's not a wall in the way.
 * he's facing.
 * @property previouslyAt the hero's previous location in the maze. This is used to keep track of the hero's movement.
 */
data class Hero(
    val at: Coordinate,
    val facing: Direction,
    val intent: Direction = facing,
    val previouslyAt: Coordinate = at
)

/**
 * Checks if the hero is moving.
 */
fun Hero.isMoving() = at != previouslyAt

/**
 * Gets a new hero instance at the same location but facing [to].
 */
fun Hero.face(to: Direction) = copy(facing = to)

/**
 * Gets a new hero instance with the same location and facing direction, but with a new intent.
 */
fun Hero.changeIntent(to: Direction) = copy(intent = to)

/**
 *  Moves the hero to the next cell in the direction he intends to move. If the hero intends to move towards a wall,
 *  he tries to move in the direction he's facing. If there's a wall in the way, he doesn't move.
 */
fun Hero.move(maze: Maze): HeroMovementResult {
    val nextFacing = if (maze.hasWall(at + intent)) facing else intent
    val nextCoordinate = at + nextFacing
    return if (maze.hasWall(nextCoordinate))
        HeroMovementResult(hero = copy(previouslyAt = at), action = HeroAction.STOP)
    else {
        val action = when (maze[nextCoordinate]) {
            Cell.PELLET -> HeroAction.EAT_PELLET
            Cell.POWER_PELLET -> HeroAction.EAT_POWER_PELLET
            else -> HeroAction.MOVE
        }
        HeroMovementResult(hero = copy(at = nextCoordinate, facing = nextFacing, previouslyAt = at), action = action)
    }
}

/**
 * The hero's action.
 */
enum class HeroAction {
    MOVE,
    EAT_PELLET,
    EAT_POWER_PELLET,
    STOP
}

/**
 * The result of the hero's movement.
 */
data class HeroMovementResult(val hero: Hero, val action: HeroAction)