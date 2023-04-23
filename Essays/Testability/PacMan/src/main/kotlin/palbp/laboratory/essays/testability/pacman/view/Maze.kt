package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Cell
import palbp.laboratory.essays.testability.pacman.domain.Coordinate
import palbp.laboratory.essays.testability.pacman.domain.Maze
import pt.isel.canvas.Canvas

/**
 * Draws the maze on this canvas.
 */
fun Canvas.draw(maze: Maze) {
    drawLayout()
    maze.cells.forEachIndexed { index, cell ->
        if (cell == Cell.PELLET) {
            drawPellet(Coordinate(index).toPoint())
        } else if (cell == Cell.POWER_PELLET) {
            drawPowerPellet(Coordinate(index).toPoint())
        }
    }
}
