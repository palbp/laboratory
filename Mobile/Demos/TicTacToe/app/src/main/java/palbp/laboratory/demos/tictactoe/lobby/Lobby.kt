package palbp.laboratory.demos.tictactoe.lobby

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import palbp.laboratory.demos.tictactoe.TAG
import palbp.laboratory.demos.tictactoe.preferences.UserInfo

/**
 * Abstraction that characterizes the game's lobby, using a push-style
 * interaction model, a.k.a reactive style.
 */
interface Lobby {
    val players: Flow<List<UserInfo>>
}

/**
 * Abstraction that characterizes the game's lobby, using a pull-style
 * interaction model.
 * TODO: Remove it after next class.
 */
interface LobbyPullStyle {
    suspend fun getPlayers(): List<UserInfo>
}

/**
 * Fake implementation, for demo purposes.
 * TODO: Remove it after next class.
 */
class FakeLobby : LobbyPullStyle, Lobby {
    private var count = 1

    private fun getCurrentListOfPlayers(): List<UserInfo> {
        val list = buildList {
            repeat(5) {
                add(UserInfo("My Nick $it", "$count This is my $it moto"))
            }
        }
        count += 1
        return list
    }

    override suspend fun getPlayers(): List<UserInfo> = getCurrentListOfPlayers()

    override val players: Flow<List<UserInfo>>
        get() = flow {
            while(true) {
                delay(5000)
                Log.v(TAG, "Lobby is emitting to the flow the version $count of the players' list")
                emit(getCurrentListOfPlayers())
            }
        }
}

