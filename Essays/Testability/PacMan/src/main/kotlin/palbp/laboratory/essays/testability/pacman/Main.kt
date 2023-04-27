package palbp.laboratory.essays.testability.pacman

import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Step
import palbp.laboratory.essays.testability.pacman.view.ARENA_VIEW_HEIGHT
import palbp.laboratory.essays.testability.pacman.view.ARENA_VIEW_WIDTH
import palbp.laboratory.essays.testability.pacman.view.SCALE
import pt.isel.canvas.BLACK
import pt.isel.canvas.Canvas
import pt.isel.canvas.DOWN_CODE
import pt.isel.canvas.LEFT_CODE
import pt.isel.canvas.RIGHT_CODE
import pt.isel.canvas.UP_CODE
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart

/**
 * Implementation of a Pac-Man clone, based on [this version](https://freepacman.org/)
 * For information about the game, namely, scoring system and movement patterns,
 * see [here](https://pacman.fandom.com/wiki/Pac-Man_(game))
 */
fun main() {

    onStart {
        loadClips("sounds/munch_1", "sounds/munch_2", "sounds/siren_1")
        val canvas = Canvas(width = ARENA_VIEW_WIDTH, height = ARENA_VIEW_HEIGHT, background = BLACK)
        var world = World(movementStep = Step(current = 0, total = SCALE.toInt() * 2))
        canvas.draw(world)

        // playSoundLoop("sounds/siren_1")

        canvas.onKeyPressed {
            val direction: Direction? = when (it.code) {
                DOWN_CODE -> Direction.DOWN
                UP_CODE -> Direction.UP
                LEFT_CODE -> Direction.LEFT
                RIGHT_CODE -> Direction.RIGHT
                else -> null
            }
            if (direction != null) {
                world = world.changeHeroDirection(direction)
            }
        }

        canvas.onTimeProgress(1000 / 40) {
            world = world.doStep()
            canvas.redraw(world)
        }
    }

    onFinish { }
}
