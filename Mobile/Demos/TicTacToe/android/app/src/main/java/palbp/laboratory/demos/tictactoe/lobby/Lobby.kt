package palbp.laboratory.demos.tictactoe.lobby

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import palbp.laboratory.demos.tictactoe.preferences.UserInfo
import java.util.*

data class PlayerInfo(val info: UserInfo, val id: UUID = UUID.randomUUID())

/**
 * Abstraction that characterizes the game's lobby.
 */
interface Lobby {
    suspend fun getPlayers(): List<PlayerInfo>
    fun enter(localPlayer: PlayerInfo): Flow<List<PlayerInfo>>
    fun leave()
}

/**
 * Fake implementation, for demo purposes.
 */
class FakeLobby : Lobby {
    private var count = 1

    private var localPlayerInfo: PlayerInfo? = null

    private fun getCurrentListOfPlayers(): List<PlayerInfo> {
        val list = buildList {
            val localPlayer = localPlayerInfo
            if (localPlayer != null)
                add(localPlayer)
            repeat(5) {
                add(PlayerInfo(
                    UserInfo("My Nick $it", "$count This is my $it moto")
                ))
            }
        }
        count += 1
        return list
    }

    override suspend fun getPlayers(): List<PlayerInfo> = getCurrentListOfPlayers()

    override fun enter(localPlayer: PlayerInfo): Flow<List<PlayerInfo>> = flow {
        localPlayerInfo = localPlayer
        while (localPlayerInfo != null) {
            emit(getCurrentListOfPlayers())
        }
    }

    override fun leave() {
        localPlayerInfo = null
    }
}

