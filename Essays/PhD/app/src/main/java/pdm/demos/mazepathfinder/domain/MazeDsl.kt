package pdm.demos.mazepathfinder.domain

class MazeBuilder {
    private val rows = mutableListOf<List<Maze.Cell>>()

    fun row(init: RowBuilder.() -> Unit) {
        val rowBuilder = RowBuilder(rowIndex = rows.size)
        rowBuilder.init()
        rows.add(rowBuilder.build())
    }

    fun build(): Maze {
        return Maze(rows.toList())
    }
}

class RowBuilder(private val rowIndex: Int) {
    private val cells = mutableListOf<Maze.Cell>()

    fun cell(init: CellBuilder.() -> Unit) {
        val cellBuilder = CellBuilder(row = rowIndex, column = cells.size)
        cellBuilder.init()
        cells.add(cellBuilder.build())
    }

    fun build(): List<Maze.Cell> = cells.toList()
}

class CellBuilder(private val row: Int, private val column: Int) {

    var label: String? = null
    var walls: Set<Maze.Wall> = emptySet()
    var type: Maze.Annotation? = null

    fun build(): Maze.Cell = Maze.Cell(row, column, label, walls, type)
    fun setStart() {
        type = Maze.Annotation.START
    }

    fun setGoal() {
        type = Maze.Annotation.GOAL
    }
}

fun maze(init: MazeBuilder.() -> Unit): Maze {
    val mazeBuilder = MazeBuilder()
    mazeBuilder.init()
    return mazeBuilder.build()
}
