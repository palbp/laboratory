package essays.mc

import kotlin.collections.plus

/**
 * The game mode.
 */
enum class Mode { IDLE, PLAYING, REPLAYING, STOPPED }

/**
 * Representation of the game.
 * @property world      the current world snapshot.
 * @property mode       the current game mode
 * @property replayInfo the replay information
 */
data class Game(val world: World = World(), val mode: Mode = Mode.IDLE, val replayInfo: Replay = Replay())

/**
 * Starts the game.
 */
fun Game.start(): Game = Game(mode = Mode.PLAYING)

/**
 * Stops the game, keeping the snapshot history and the current world state.
 */
fun Game.stop(): Game = copy(mode = Mode.STOPPED)

/**
 *
 */
fun Game.reset(): Game = Game()

/**
 * Fires a defender missile at the given location if the game is in [Mode.PLAYING]. Otherwise, it returns the
 * same game state.
 */
fun Game.fireDefenderMissile(targetX: Int, targetY: Int): Game {
    val newWorld = world.addDefenderMissile(Location(targetX, targetY))
    return copy(world = newWorld, replayInfo = replayInfo.addSnapshot(newWorld))
}

/**
 * Fires an attacker missile if the game is in [Mode.PLAYING]. Otherwise, it returns the same game state.
 */
fun Game.fireAttackerMissile(): Game {
    val newWorld = world.addMissile()
    return copy(world = newWorld, replayInfo = replayInfo.addSnapshot(newWorld))
}

/**
 * Computes the next game state.
 */
fun Game.next(): Game = when (mode) {
    Mode.PLAYING -> {
        val newWorld = world.computeNext()
        copy(world = newWorld, replayInfo = replayInfo.addSnapshot(newWorld))
    }

    Mode.REPLAYING -> computeReplaySnapshot()
    else -> this
}

/**
 * Replays the game history in the given [mode]. Note that an [Mode.IDLE] game doesn't have a history to replay and
 * therefore will immediately return to the [Mode.STOPPED] state.
 */
fun Game.replay(mode: ReplayMode): Game = if (mode == ReplayMode.FORWARD) {
    copy(
        world = replayInfo.history.first(),
        mode = Mode.REPLAYING,
        replayInfo = replayInfo.copy(currentIndex = 0, mode = ReplayMode.FORWARD)
    )
} else {
    copy(
        world = replayInfo.history.last(),
        mode = Mode.REPLAYING,
        replayInfo = replayInfo.copy(currentIndex = replayInfo.history.size - 1, mode = ReplayMode.BACKWARD)
    )
}

/**
 * Computes the next game state if this game is in [Mode.REPLAYING]. Otherwise, it returns the same game state.
 */
private fun Game.computeReplaySnapshot(): Game {

    fun nextIndex(info: Replay) = if (info.mode == ReplayMode.FORWARD) info.currentIndex + 1
    else info.currentIndex - 1

    fun isReplayOver(info: Replay, history: List<World>) =
        if (info.mode == ReplayMode.FORWARD) info.currentIndex == history.size - 1
        else info.currentIndex == 0

    return when {
        mode != Mode.REPLAYING -> this
        isReplayOver(replayInfo, replayInfo.history) -> stop()

        else -> copy(
            world = replayInfo.history[nextIndex(replayInfo)],
            replayInfo = replayInfo.copy(currentIndex = nextIndex(replayInfo))
        )
    }
}

/**
 * The replay mode.
 */
enum class ReplayMode { FORWARD, BACKWARD }

const val MAX_REPLAY_HISTORY = 10_000

/**
 * The data representation required for replaying.
 * @property history       The list of world snapshots that comprise the recorded game history
 * @property currentIndex  The index of the current snapshot in the history
 * @property mode          The replay mode
 */
data class Replay(
    val history: List<World> = getListOf(),
    val currentIndex: Int = 0,
    val mode: ReplayMode = ReplayMode.FORWARD
)

/**
 * Adds a [world] snapshot to the replay history. If the added snapshot pushes replay history above the allowed capacity,
 * the oldest snapshot is removed and the new one is added.
 */
fun Replay.addSnapshot(world: World): Replay =
    copy(history = (if (history.size >= MAX_REPLAY_HISTORY) history.drop(1) else history) + world)