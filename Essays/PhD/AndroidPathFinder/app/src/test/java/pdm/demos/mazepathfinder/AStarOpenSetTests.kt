package pdm.demos.mazepathfinder

import org.junit.Test
import pdm.demos.mazepathfinder.domain.AStarOpenSet
import pdm.demos.mazepathfinder.domain.GraphNode
import pdm.demos.mazepathfinder.domain.Maze.Cell
import pdm.demos.mazepathfinder.domain.euclideanDistanceTo

class AStarOpenSetTests {

    @Test
    fun `add should add the node to the set`() {
        // Arrange
        val openSet = AStarOpenSet(h = { 0.0 })
        val node = GraphNode(Cell(0, 0))

        // Act
        openSet.add(node)

        // Assert
        assert(openSet.contents.contains(node))
    }

    @Test
    fun `add on a non empty set adds the node and keeps the set ordered`() {
        // Arrange
        val goal = Cell(row = 0, column = 4, label = "E")
        val openSet = AStarOpenSet(h = { it.cell.euclideanDistanceTo(goal) })
        val start = GraphNode(Cell(row = 4, column = 4, label = "Z"))

        // Act
        openSet.add(
            GraphNode(
                Cell(row = 4, column = 3, label = "Y"),
                accumulatedCost = 1,
                parent = start
            )
        )
        openSet.add(
            GraphNode(
                Cell(row = 3, column = 4, label = "T"),
                accumulatedCost = 1,
                parent = start
            )
        )

        // Assert
        assert(openSet.contents[0].cell.label == "T") {
            "Expected 'T' on the first position but got ${openSet.contents[0].cell.label}"
        }
        assert(openSet.contents[1].cell.label == "Y") {
            "Expected 'Y' on the second position but got ${openSet.contents[0].cell.label}"
        }
    }

    @Test
    fun `addAll on a non empty set adds the nodes and keeps the set ordered`() {
        // Arrange
        val goal = Cell(row = 0, column = 4, label = "E")
        val openSet = AStarOpenSet(h = { it.cell.euclideanDistanceTo(goal) })
        val start = GraphNode(Cell(row = 4, column = 4, label = "Z"))
        val y =
            GraphNode(Cell(row = 4, column = 3, label = "Y"), accumulatedCost = 1, parent = start)
        val t =
            GraphNode(Cell(row = 3, column = 4, label = "T"), accumulatedCost = 1, parent = start)
        openSet.add(y)

        // Act
        openSet.addAll(
            listOf(
                GraphNode(Cell(row = 4, column = 2, label = "X"), accumulatedCost = 2, parent = y),
                GraphNode(Cell(row = 2, column = 4, label = "S"), accumulatedCost = 2, parent = t)
            )
        )

        // Assert
        assert(openSet.contents[0].cell.label == "S") {
            "Expected 'S' on the first position but got ${openSet.contents[1].cell.label}"
        }
    }
}