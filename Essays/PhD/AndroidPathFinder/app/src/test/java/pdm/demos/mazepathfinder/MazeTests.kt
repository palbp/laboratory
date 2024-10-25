package pdm.demos.mazepathfinder

import org.junit.Test
import pdm.demos.mazepathfinder.domain.Maze.Wall
import pdm.demos.mazepathfinder.domain.buildTheSampleMaze
import pdm.demos.mazepathfinder.domain.maze

class MazeTests {

    @Test(expected = IllegalArgumentException::class)
    fun create_a_maze_with_no_starting_point_fails() {
        maze {
            row {
                cell { walls = setOf(Wall.NORTH) }
                cell { walls = setOf(Wall.NORTH) }
            }
            row {
                cell { walls = setOf(Wall.SOUTH) }
                cell { walls = setOf(Wall.SOUTH) }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_a_maze_with_multiple_starting_points_fails() {
        maze {
            row {
                cell { setStart() }
                cell { setStart() }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_a_maze_with_a_single_starting_point_and_no_goal_fails() {
        maze {
            row {
                cell { setStart() }
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_a_maze_with_a_single_starting_point_and_multiple_goals_fails() {
        maze {
            row {
                cell { setStart() }
                cell { setGoal() }
            }
            row {
                cell { }
                cell { setGoal() }
            }
        }
    }

    @Test
    fun create_a_maze_with_a_single_starting_point_and_a_single_goal_succeeds() {
        maze {
            row {
                cell { setStart(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.WEST) }
                cell { setGoal(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.EAST) }
            }
        }
    }

    @Test
    fun startCell_returns_the_starting_cell() {
        val maze = maze {
            row {
                cell { setStart(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.WEST) }
                cell { setGoal(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.EAST) }
            }
        }
        assert(maze.startCell.isStart) { "The start cell is not marked as start" }
    }

    @Test
    fun goalCell_returns_the_goal_cell() {
        val maze = maze {
            row {
                cell { setStart(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.WEST) }
                cell { setGoal(); walls = setOf(Wall.NORTH, Wall.SOUTH, Wall.EAST) }
            }
        }

        assert(maze.goalCell.isGoal) { "The goal cell is not marked as goal" }
    }

    @Test
    fun buildTheSampleMaze_succeeds() {
        buildTheSampleMaze()
    }
}