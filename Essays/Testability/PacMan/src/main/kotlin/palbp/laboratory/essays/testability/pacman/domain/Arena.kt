package palbp.laboratory.essays.testability.pacman.domain

/**
 * Represents coordinates in the arena.
 */
data class Coordinate(val row: Int, val column: Int) {
    init {
        require(row in 0 until ARENA_HEIGHT)
        require(column in 0 until ARENA_WIDTH)
    }

    constructor(index: Int) : this(row = index / ARENA_WIDTH, column = index % ARENA_WIDTH)
}

/**
 * The arena's cells
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
 * Represents the game arena. Instances are immutable.
 */
data class Arena(val cells: List<Cell>, val pacMan: Hero)

/**
 * Creates an [Arena] instance from the given description. If a description is not provided, the created arena layout
 * is as defined by [ARENA_LAYOUT]. The arena's description syntax is as described in layout.kt
 * @param [from] the arena description
 * @return the newly created instance.
 */
fun createArena(from: String = ARENA_LAYOUT) = Arena(
    buildList {
        from.forEach { symbol ->
            val cell = if (isObstacle(symbol)) Cell.WALL else Cell.EMPTY
            add(cell)
        }
    },
    Hero(at = pacManStartingPosition, facing = Direction.LEFT)
)
