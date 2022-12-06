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

    private var onGoingGame: Game? = null

    private fun subscribeGameStateUpdated(id: String, flow: ProducerScope<Game>) =
        db.collection(ONGOING)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                val currentGame = onGoingGame
                checkNotNull(currentGame)
                when {
                    error != null -> flow.close(error)
                    snapshot != null -> {
                        val game = Game(currentGame.localPlayerMarker, snapshot.toBoard())
                        onGoingGame = game
                        flow.trySend(game)
                    }
                }
            }

    private suspend fun publishGame(game: Game, challengerId: String) {
        db.collection(ONGOING)
            .document(challengerId)
            .set(game.board.toDocumentContent())
            .await()
    }

    override fun start(localPLayer: PlayerInfo, challenge: Challenge): Flow<Game> {
        check(onGoingGame == null)
        val newGame = Game(
            localPlayerMarker =
                if (localPLayer == challenge.challenged) Marker.firstToMove.other
                else Marker.firstToMove,
            board = Board()
        )
        onGoingGame = newGame
        return callbackFlow {
            var gameSubscription: ListenerRegistration? = null

            try {
                if (localPLayer == challenge.challenged)
                    publishGame(newGame, challenge.challenger.id.toString())

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
        TODO("Not yet implemented")
    }

    override suspend fun forfeit() {
        TODO("Not yet implemented")
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
    BOARD_FIELD to toMovesList().joinToString(separator = "") { it?.name ?: " " }
)

/**
 * Extension function to convert documents stored in the Firestore DB
 * into [Board] instances.
 */
fun DocumentSnapshot.toBoard(): Board {
    val moves = data?.get(BOARD_FIELD) as String
    val turn = Marker.valueOf(data?.get(TURN_FIELD) as String)
    return Board.fromMovesList(turn, moves.toMovesList())
}

/**
 * Converts this string to a list of moves in the board
 */
fun String.toMovesList(): List<Marker?> = map {
    when (it) {
        Marker.CROSS.name.first() -> Marker.CROSS
        Marker.CIRCLE.name.first() -> Marker.CIRCLE
        else -> null
    }
}