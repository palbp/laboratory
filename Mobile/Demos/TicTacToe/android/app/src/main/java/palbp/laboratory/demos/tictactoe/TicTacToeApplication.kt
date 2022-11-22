package palbp.laboratory.demos.tictactoe

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import palbp.laboratory.demos.tictactoe.game.lobby.Lobby
import palbp.laboratory.demos.tictactoe.game.lobby.LobbyFirebase
import palbp.laboratory.demos.tictactoe.preferences.UserInfoRepository
import palbp.laboratory.demos.tictactoe.preferences.UserInfoRepositorySharedPrefs

const val TAG = "TicTacToeApp"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    val userInfoRepo: UserInfoRepository
    val lobby: Lobby
}

/**
 * The application class to be used as a Service Locator.
 */
class TicTacToeApplication : DependenciesContainer, Application() {

    override val userInfoRepo: UserInfoRepository
        get() = UserInfoRepositorySharedPrefs(this)

    override val lobby: Lobby
        get() = LobbyFirebase(Firebase.firestore)
}

