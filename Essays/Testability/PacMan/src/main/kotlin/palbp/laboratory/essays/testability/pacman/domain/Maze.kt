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
    WALL
}

/**
 * Represents the game maze. Instances are immutable.
 * @property [cells] the maze's cells
 */
data class Maze(val cells: List<Cell>)

/**
 * Creates an [Maze] instance from the given description. If a description is not provided, the created maze
 * is as defined by [MAZE_LAYOUT]. The layout's description syntax is as described in layout.kt
 * @param [from] the maze's description
 * @return the newly created instance.
 */
fun createMaze(from: String = MAZE_LAYOUT) = Maze(
    buildList {
        from.forEach { symbol ->
            val cell = if (isObstacle(symbol)) Cell.WALL else Cell.EMPTY
            add(cell)
        }
    }
)

/**
 * Checks whether there's a wall in the given coordinate.
 */
fun Maze.hasWall(at: Coordinate) = cells[at.toIndex()] == Cell.WALL
