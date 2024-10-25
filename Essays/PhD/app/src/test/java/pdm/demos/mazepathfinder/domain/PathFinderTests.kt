package pdm.demos.mazepathfinder.domain

import org.junit.Test
import pdm.demos.mazepathfinder.domain.Maze.Cell
import kotlinx.coroutines.test.runTest

class PathFinderTests {

    @Test
    fun rebuildPath_with_a_root_node_returns_a_list_with_only_the_root_node() {
        // Arrange
        val root = GraphNode(Cell(0, 0))
        // Act
        val path = rebuildPath(root)
        // Assert
        assert(path.size == 1) { "The path should have only one cell" }
        assert(path[0] == Cell(0, 0)) { "The path should have the root cell" }
    }

    @Test
    fun rebuildPath_with_a_node_and_its_parent_returns_a_list_with_the_two_cells() {
        // Arrange
        val parent = GraphNode(Cell(0, 0))
        val child = GraphNode(Cell(1, 0), parent)
        // Act
        val path = rebuildPath(child)
        // Assert
        assert(path.size == 2) { "The path should have two cells" }
        assert(path[0] == Cell(0, 0)) { "The path should have the parent cell first" }
        assert(path[1] == Cell(1, 0)) { "The path should have the child cell second" }
    }
}