package pdm.demos.mazepathfinder

import org.junit.Test
import pdm.demos.mazepathfinder.domain.Maze
import pdm.demos.mazepathfinder.domain.Maze.Annotation.GOAL
import pdm.demos.mazepathfinder.domain.Maze.Annotation.START

class CellTests {

    @Test
    fun cell_with_no_type_annotation_is_neither_starting_cell_nor_goal_cell() {
        val sut = Maze.Cell(0, 0)
        assert(!sut.isStart) { "Cell is a starting cell" }
        assert(!sut.isGoal) { "Cell is a goal cell" }
    }

    @Test
    fun cell_with_start_type_annotation_is_starting_cell_and_not_a_goal_cell() {
        val sut = Maze.Cell(0, 0, annotation = START)
        assert(sut.isStart) { "Cell is not a starting cell" }
        assert(!sut.isGoal) { "Cell is a goal cell" }
    }

    @Test
    fun cell_with_goal_type_annotation_is_goal_cell_and_not_a_starting_cell() {
        val sut = Maze.Cell(0, 0, annotation = GOAL)
        assert(!sut.isStart) { "Cell is a starting cell" }
        assert(sut.isGoal) { "Cell is not a goal cell" }
    }

    @Test(expected = IllegalArgumentException::class)
    fun cell_with_negative_row_fails() {
        Maze.Cell(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun cell_with_negative_column_fails() {
        Maze.Cell(0, -1)
    }
}