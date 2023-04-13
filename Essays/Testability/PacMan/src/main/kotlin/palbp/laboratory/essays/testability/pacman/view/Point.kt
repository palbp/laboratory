package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Coordinate

/**
 * Represents points on the screen, with the origin in the top left corner.
 */
data class Point(val x: Int, val y: Int) {
    init {
        require(x >= 0 && y >= 0)
    }
}

/**
 * Converts the given coordinate to a point.
 */
fun Coordinate.toPoint() = Point(column * CELL_SIZE, row * CELL_SIZE)
