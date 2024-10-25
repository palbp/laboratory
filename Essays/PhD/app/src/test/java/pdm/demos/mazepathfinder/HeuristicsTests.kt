package pdm.demos.mazepathfinder

import org.junit.Test
import pdm.demos.mazepathfinder.domain.Maze
import pdm.demos.mazepathfinder.domain.euclideanDistanceTo
import pdm.demos.mazepathfinder.domain.manhattanDistanceTo

class HeuristicsTests {

    @Test
    fun `manhattan distance between two cells is correctly computed`() {
        // Arrange
        val origin = Maze.Cell(1, 1)
        val goal = Maze.Cell(4, 5)

        // Act
        val distance = origin.manhattanDistanceTo(goal)

        // Assert
        val expectedDistance = 7.0
        assert(distance == expectedDistance) {
            "Expected distance id $expectedDistance, but got $distance"
        }
    }

    @Test
    fun `euclidean distance between two cells is correctly computed`() {
        // Arrange
        val cell1 = Maze.Cell(1, 1)
        val cell2 = Maze.Cell(4, 5)

        // Act
        val distance = cell1.euclideanDistanceTo(cell2)

        // Assert
        val expectedDistance = 5.0
        assert(distance == expectedDistance) {
            "Expected distance id $expectedDistance, but got $distance"
        }
    }
}