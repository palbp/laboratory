package palbp.laboratory.demos.tictactoe

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import palbp.laboratory.demos.tictactoe.game.lobby.adapters.LobbyFirebase
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Lobby
import palbp.laboratory.demos.tictactoe.game.play.adapters.MatchFirebase
import palbp.laboratory.demos.tictactoe.game.play.domain.Match
import palbp.laboratory.demos.tictactoe.preferences.UserInfoRepositorySharedPrefs
import palbp.laboratory.demos.tictactoe.preferences.domain.UserInfoRepository

const val TAG = "TicTacToeApp"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    val userInfoRepo: UserInfoRepository
    val lobby: Lobby
    val match: Match
}

/**
 * The application class to be used as a Service Locator.
 */
class TicTacToeApplication : DependenciesContainer, Application() {

    private val emulatedFirestoreDb: FirebaseFirestore by lazy {
        Firebase.firestore.also {
            it.useEmulator("10.0.2.2", 8080)
            it.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
        }
    }

    private val realFirestoreDb: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    override val userInfoRepo: UserInfoRepository
        get() = UserInfoRepositorySharedPrefs(this)

    override val lobby: Lobby
        get() = LobbyFirebase(emulatedFirestoreDb)

    override val match: Match
        get() = MatchFirebase(emulatedFirestoreDb)
}

