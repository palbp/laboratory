package essays.mc

import kotlinx.collections.immutable.persistentListOf
import pt.isel.canvas.BLACK
import pt.isel.canvas.Canvas
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart

const val START_KEY = 's'
const val STOP_KEY = 's'
const val RESET_KEY = 's'
const val REPLAY_FORWARD_KEY = 'f'
const val REPLAY_BACKWARD_KEY = 'b'

/**
 * Constant used to switch on/off the use of persistent data structures. For demo purposes only.
 */
const val USE_PERSISTENT_LISTS = true

/**
 * Used for demo purposes only. The idea is to show the consequences of using persistent data structures
 * instead of using the standard Kotlin immutable collections.
 */
fun <T> getListOf() = if (USE_PERSISTENT_LISTS) persistentListOf<T>() else listOf<T>()

/**
 * Implementation of simplification of Atari's Missile Command
 * Inspiration source: https://games.aarp.org/games/atari-missile-command
 *
 * Persistent collections for Kotlin:
 * https://github.com/Kotlin/kotlinx.collections.immutable
 */
fun main() {

    val startTime = System.currentTimeMillis()

    onStart {
        var game = Game()
        val canvas = Canvas(game.world.width, game.world.height, BLACK)
        drawGame(canvas, game)

        val groundDmz = game.world.height - (game.world.groundHeight + MAX_RADIUS)
        canvas.onMouseDown {
            if (game.mode == Mode.PLAYING && it.y < groundDmz) {
                game = game.fireDefenderMissile(it.x, it.y)
            }
        }

        canvas.onTimeProgress(1500) {
            if (game.mode == Mode.PLAYING) {
                game = game.fireAttackerMissile()
            }
        }

        canvas.onTimeProgress(period = 25) { elapsedTime ->
            game = game.next()
            drawGame(canvas, game)
        }

        canvas.onKeyPressed {
            game = when {
                game.mode == Mode.IDLE && it.char == START_KEY -> game.start()
                game.mode == Mode.PLAYING && it.char == STOP_KEY -> game.stop()
                game.mode == Mode.STOPPED ->
                    when (it.char) {
                        RESET_KEY -> game.reset()
                        REPLAY_FORWARD_KEY -> game.replay(mode = ReplayMode.FORWARD)
                        REPLAY_BACKWARD_KEY -> game.replay(mode = ReplayMode.BACKWARD)
                        else -> game
                    }

                else -> game
            }
        }

        onFinish {
            println("Game over!")
            println("Elapsed time: ${System.currentTimeMillis() - startTime} ms")
            println("Number of snapshots: ${game.replayInfo.history.size}")
        }
    }
}



