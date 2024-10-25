package pdm.demos.mazepathfinder.domain

import pdm.demos.mazepathfinder.domain.Maze.Annotation
import pdm.demos.mazepathfinder.domain.Maze.Wall

/**
 * A static representation of a maze. Paths in the maze are represented by a list of cells, not by
 * instances of this class.
 * Maze requirements are:
 *  - A maze must have a single start cell and a single goal cell.
 *  - Cells in the maze perimeter must have walls on the outside.
 */
data class Maze(private val rows: List<List<Cell>> = emptyList()) {

    init {
        rows.flatMap { it }.let { cells ->
            require(cells.count { it.isStart } == 1) { "Maze must have exactly one start cell" }
            require(cells.count { it.isGoal } == 1) { "Maze must have exactly one goal cell" }
            require(cells.filter { it.row == 0 }
                .all { it.hasNorthWall }) { "Maze must have a wall on the north perimeter" }
            require(cells.filter { it.row == rowCount - 1 }
                .all { it.hasSouthWall }) { "Maze must have a wall on the south perimeter" }
            require(cells.filter { it.column == 0 }
                .all { it.hasWestWall }) { "Maze must have a wall on the west perimeter" }
            require(cells.filter { it.column == rows[it.row].size - 1 }
                .all { it.hasEastWall }) { "Maze must have a wall on the east perimeter" }
        }
    }

    enum class Wall { NORTH, EAST, SOUTH, WEST }

    enum class Annotation { START, GOAL }

    data class Cell(
        val row: Int,
        val column: Int,
        val label: String? = null,
        val walls: Set<Wall> = emptySet(),
        val annotation: Annotation? = null
    ) {
        init {
            require(row >= 0) { "Cell row must be greater than or equal to 0" }
            require(column >= 0) { "Cell column must be greater than or equal to 0" }
            require(label == null || label.isNotBlank()) { "Non-null label cannot be blank" }
        }

        val isStart: Boolean
            get() = annotation == Annotation.START

        val isGoal: Boolean
            get() = annotation == Annotation.GOAL

        val hasNorthWall: Boolean = walls.contains(Wall.NORTH)
        val hasEastWall: Boolean = walls.contains(Wall.EAST)
        val hasSouthWall: Boolean = walls.contains(Wall.SOUTH)
        val hasWestWall: Boolean = walls.contains(Wall.WEST)

        override fun toString(): String = label ?: "($row, $column)"
    }

    val rowCount: Int
        get() = rows.size

    operator fun get(rowIndex: Int): List<Cell> {
        require(rowIndex in 0 until rowCount) { "Invalid row index" }
        return rows[rowIndex]
    }

    val startCell by lazy { rows.flatMap { it }.first { it.isStart } }
    val goalCell by lazy { rows.flatMap { it }.first { it.isGoal } }

    fun getNeighbors(cell: Cell): List<Cell> {
        val neighborsDirections = Wall.entries.filter { !cell.walls.contains(it) }
        return neighborsDirections.map { direction ->
            when (direction) {
                Wall.NORTH -> rows[cell.row - 1][cell.column]
                Wall.EAST -> rows[cell.row][cell.column + 1]
                Wall.SOUTH -> rows[cell.row + 1][cell.column]
                Wall.WEST -> rows[cell.row][cell.column - 1]
            }
        }
    }
}

/**
 * Creates a maze using the Maze DSL.
 */
fun buildTheSampleMaze(): Maze = maze {
    row {
        cell { label = "A"; walls = setOf(Wall.NORTH, Wall.WEST) }
        cell { label = "B"; walls = setOf(Wall.NORTH, Wall.EAST, Wall.SOUTH) }
        cell { label = "C"; walls = setOf(Wall.NORTH, Wall.WEST) }
        cell { label = "D"; walls = setOf(Wall.NORTH, Wall.SOUTH) }
        cell { label = "E"; walls = setOf(Wall.NORTH, Wall.EAST); setGoal() }
    }
    row {
        cell { label = "F"; walls = setOf(Wall.WEST, Wall.SOUTH) }
        cell { label = "G"; walls = setOf(Wall.NORTH, Wall.SOUTH) }
        cell { label = "H"; walls = setOf(Wall.SOUTH) }
        cell { label = "I"; walls = setOf(Wall.NORTH, Wall.EAST) }
        cell { label = "J"; walls = setOf(Wall.WEST, Wall.SOUTH, Wall.EAST) }
    }
    row {
        cell { label = "K"; walls = setOf(Wall.WEST, Wall.NORTH, Wall.EAST) }
        cell { label = "L"; walls = setOf(Wall.WEST, Wall.NORTH) }
        cell { label = "M"; walls = setOf(Wall.NORTH) }
        cell { label = "N"; walls = setOf(Wall.SOUTH) }
        cell { label = "O"; walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.EAST) }
    }
    row {
        cell { label = "P"; walls = setOf(Wall.WEST, Wall.EAST) }
        cell { label = "Q"; walls = setOf(Wall.WEST, Wall.EAST) }
        cell { label = "R"; walls = setOf(Wall.WEST, Wall.SOUTH) }
        cell { label = "S"; walls = setOf(Wall.NORTH, Wall.SOUTH) }
        cell { label = "T"; walls = setOf(Wall.NORTH, Wall.EAST) }
    }
    row {
        cell { label = "U"; walls = setOf(Wall.WEST, Wall.SOUTH) }
        cell { label = "V"; walls = setOf(Wall.SOUTH) }
        cell { label = "X"; walls = setOf(Wall.NORTH, Wall.SOUTH) }
        cell { label = "Y"; walls = setOf(Wall.NORTH, Wall.SOUTH) }
        cell { label = "Z"; walls = setOf(Wall.EAST, Wall.SOUTH); setStart() }
    }
}