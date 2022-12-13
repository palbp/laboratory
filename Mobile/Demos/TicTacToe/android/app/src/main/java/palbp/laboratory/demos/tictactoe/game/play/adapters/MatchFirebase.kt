package palbp.laboratory.demos.tictactoe.game.play.adapters

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo
import palbp.laboratory.demos.tictactoe.game.play.domain.*

class MatchFirebase(private val db: FirebaseFirestore) : Match {

    private var onGoingGame: Pair<Game, String>? = null

    private fun subscribeGameStateUpdated(id: String, flow: ProducerScope<Game>) =
        db.collection(ONGOING)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                val currentGame = checkNotNull(onGoingGame)
                when {
                    error != null -> flow.close(error)
                    snapshot != null -> {
                        snapshot.toBoardOrNull()?.let {
                            val game = Game(currentGame.first.localPlayerMarker, it)
                            onGoingGame = onGoingGame?.copy(first = game)
                            flow.trySend(game)
                        }
                    }
                }
            }

    private suspend fun publishGame(game: Game, gameId: String) {
        db.collection(ONGOING)
            .document(gameId)
            .set(game.board.toDocumentContent())
            .await()
    }

    private suspend fun updateGame(game: Game, gameId: String) {
        db.collection(ONGOING)
            .document(gameId)
            .set(game.board.toDocumentContent())
            .await()
    }

    override fun start(localPlayer: PlayerInfo, challenge: Challenge): Flow<Game> {
        check(onGoingGame == null)

        return callbackFlow {
            val newGame = Game(
                localPlayerMarker = getLocalPlayerMarker(localPlayer, challenge),
                board = Board()
            )
            val gameId = challenge.challenger.id.toString()
            onGoingGame = Pair(newGame, gameId)

            var gameSubscription: ListenerRegistration? = null
            try {
                if (localPlayer == challenge.challenged)
                    publishGame(newGame, gameId)

                gameSubscription = subscribeGameStateUpdated(
                    id = challenge.challenger.id.toString(),
                    flow = this
                )
            } catch (e: Throwable) {
                close(e)
            }

            awaitClose {
                gameSubscription?.remove()
            }
        }
    }

    override suspend fun makeMove(at: Coordinate) {
        onGoingGame = checkNotNull(onGoingGame).also {
            val game = it.copy(first = it.first.makeMove(at))
            updateGame(game.first, game.second)
        }
    }

    override suspend fun forfeit() {
        onGoingGame = checkNotNull(onGoingGame).also {

        }
    }
}

/**
 * Names of the fields used in the document representations.
 */
const val ONGOING = "ongoing"
const val TURN_FIELD = "turn"
const val BOARD_FIELD = "board"

/**
 * [Board] extension function used to convert an instance to a map of key-value
 * pairs containing the object's properties
 */
fun Board.toDocumentContent() = mapOf(
    TURN_FIELD to turn.name,
    BOARD_FIELD to toMovesList().joinToString(separator = "") {
        when (it) {
            Marker.CROSS -> "X"
            Marker.CIRCLE -> "O"
            null -> "-"
        }
    }
)

/**
 * Extension function to convert documents stored in the Firestore DB
 * into [Board] instances.
 */
fun DocumentSnapshot.toBoardOrNull(): Board? =
    data?.let {
        val moves = it[BOARD_FIELD] as String
        val turn = Marker.valueOf(it[TURN_FIELD] as String)
        Board.fromMovesList(turn, moves.toMovesList())
    }

/**
 * Converts this string to a list of moves in the board
 */
fun String.toMovesList(): List<Marker?> = map {
    when (it) {
        'X' -> Marker.CROSS
        'O' -> Marker.CIRCLE
        else -> null
    }
}
