package palbp.laboratory.essays.testability.pacman.view

/**
 * Represents points on the screen
 */
data class Point(val x: Int, val y: Int) {
    init {
        require(x >= 0 && y >= 0)
    }
}
