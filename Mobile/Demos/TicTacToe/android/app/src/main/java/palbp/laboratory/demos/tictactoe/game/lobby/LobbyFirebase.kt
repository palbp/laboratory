package palbp.laboratory.demos.tictactoe.game.lobby

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import palbp.laboratory.demos.tictactoe.preferences.UserInfo
import java.util.*

private const val LOBBY = "lobby"
private const val NICK_FIELD = "nick"
private const val MOTO_FIELD = "moto"

/**
 * Implementation of the Game's lobby using Firebase's Firestore
 */
class LobbyFirebase(private val db: FirebaseFirestore) : Lobby {

    override suspend fun getPlayers(): List<PlayerInfo> {
        try {
            val result = db.collection(LOBBY).get().await()
            return result.map { it.toPlayerInfo() }
        }
        catch (e: Throwable) {
            throw UnreachableLobbyException()
        }
    }

    private var producerScope: ProducerScope<List<PlayerInfo>>? = null

    override fun enter(localPlayer: PlayerInfo): Flow<List<PlayerInfo>> {
        check(producerScope == null)
        return callbackFlow {
            producerScope = this
            var lobbyRef: CollectionReference? = null
            try {
                lobbyRef = db.collection(LOBBY)
                lobbyRef
                    .document(localPlayer.id.toString())
                    .set(localPlayer.info.toDocumentContent())
                    .addOnFailureListener { close(it) }
            } catch (e: Throwable) {
                close(e)
            }

            val subscription = lobbyRef?.addSnapshotListener { snapshot, error ->
                when {
                    error != null -> close(error)
                    snapshot != null -> trySend(snapshot.map { it.toPlayerInfo() })
                }
            }

            awaitClose {
                subscription?.remove()
                lobbyRef?.document(localPlayer.id.toString())?.delete()
            }
        }
    }

    override fun leave() {
        producerScope?.close()
        producerScope = null
    }
}

/**
 * Extension function used to convert player info documents stored in the Firestore DB
 * into [PlayerInfo] instances.
 */
private fun QueryDocumentSnapshot.toPlayerInfo() =
    PlayerInfo(
        info = UserInfo(
            nick = data[NICK_FIELD] as String,
            moto = data[MOTO_FIELD] as String?
        ),
        id = UUID.fromString(id),
    )

/**
 * [UserInfo] extension function used to convert an instance to a map of key-value
 * pairs containing the object's properties, to be used as a payload of
 */
private fun UserInfo.toDocumentContent() = mapOf(
    NICK_FIELD to nick,
    MOTO_FIELD to moto
)

class UnreachableLobbyException : Exception()