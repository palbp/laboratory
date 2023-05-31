package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents coordinates in the maze.
 */
data class Coordinate(val row: Int, val column: Int) {
    init {
        require(isValid(row, column)) { "Invalid coordinates: ($row, $column)" }
    }

    constructor(index: Int) : this(row = index / MAZE_WIDTH, column = index % MAZE_WIDTH)
}

/**
 * Checks whether the given row and column are valid coordinates in the maze.
 */
fun isValid(row: Int, column: Int) = row in 0 until MAZE_HEIGHT && column in 0 until MAZE_WIDTH

/**
 * Converts the given coordinate to a linear index.
 */
fun Coordinate.toIndex() = row * MAZE_WIDTH + column

/**
 * Converts the given index to a coordinate.
 */
fun Int.toCoordinate() = Coordinate(this)

/**
 * The maze's cells
 */
enum class Cell {
    /**
     * A free space that can be stepped by actors
     */
    EMPTY,
    /**
     * An obstacle for all actors
     */
    WALL,
    /**
     * A cell that contains a pellet
     */
    PELLET,
    /**
     * A cell that contains a power pellet
     */
    POWER_PELLET
}

/**
 * Represents the game maze. Instances are immutable.
 * @property [cells] the maze's cells
 */
data class Maze(val cells: List<Cell>, val powerPelletsLocations: List<Coordinate>)

/**
 * Creates an [Maze] instance from the given description. If a description is not provided, the created maze
 * is as defined by [MAZE_LAYOUT]. The layout's description syntax is as described in layout.kt
 * @param [from] the maze's description
 * @return the newly created instance.
 */
fun createMaze(from: String = MAZE_LAYOUT): Maze {
    val powerPelletsIndex = mutableListOf<Coordinate>()
    val cells = from.mapIndexed { index, symbol ->
        when {
            isObstacle(symbol) -> Cell.WALL
            isPellet(symbol) -> Cell.PELLET
            isPowerPellet(symbol) -> { powerPelletsIndex.add(index.toCoordinate()); Cell.POWER_PELLET }
            else -> Cell.EMPTY
        }
    }

    return Maze(cells, powerPelletsIndex)
}

/**
 * Gets the cell at the given coordinate.
 * @param at the coordinate to get the cell from.
 * @return the cell at the given coordinate.
 */
operator fun Maze.get(at: Coordinate) = cells[at.toIndex()]

/**
 * Checks whether there's a wall in the given coordinate.
 * @param at the coordinate to check.
 * @return true if there's a wall in the given coordinate, false otherwise.
 */
fun Maze.hasWall(at: Coordinate) = cells[at.toIndex()] == Cell.WALL

/**
 * Checks whether there's a pellet (either a plain one or a power pellet) in the given coordinate.
 * @param at the coordinate to check.
 * @return true if there's a pellet in the given coordinate, false otherwise.
 */
fun Maze.hasPellet(at: Coordinate) = hasPlainPellet(at) || hasPowerPellet(at)

/**
 * Checks whether there's a power pellet in the given coordinate.
 * @param at the coordinate to check.
 * @return true if there's a power pellet in the given coordinate, false otherwise.
 */
fun Maze.hasPowerPellet(at: Coordinate) = cells[at.toIndex()] == Cell.POWER_PELLET

/**
 * Checks whether there's a plain pellet in the given coordinate.
 * @param at the coordinate to check.
 * @return true if there's a plain pellet in the given coordinate, false otherwise.
 */
fun Maze.hasPlainPellet(at: Coordinate) = cells[at.toIndex()] == Cell.PELLET

/**
 * Removes the pellet (either a plain one or a power pellet) from the given coordinate. If there's no pellet in the
 * given coordinate, the maze is not modified.
 * @param at the coordinate to remove the pellet from.
 * @return a new maze instance with the pellet removed.
 */
fun Maze.removePellet(at: Coordinate): Maze =
    if (hasPellet(at)) {
        copy(
            cells = cells.mapIndexed { index, cell ->
                if (index == at.toIndex()) Cell.EMPTY else cell
            },
            powerPelletsLocations =
            if (hasPowerPellet(at)) powerPelletsLocations.filter { it != at }
            else powerPelletsLocations
        )
    } else this
