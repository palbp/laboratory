package palbp.laboratory.demos.tictactoe.game.lobby

import kotlinx.coroutines.flow.Flow
import palbp.laboratory.demos.tictactoe.preferences.UserInfo
import java.util.*

data class PlayerInfo(val info: UserInfo, val id: UUID = UUID.randomUUID())

/**
 * Abstraction that characterizes the game's lobby.
 */
interface Lobby {
    suspend fun getPlayers(): List<PlayerInfo>
    suspend fun enter(localPlayer: PlayerInfo): List<PlayerInfo>
    fun enterAndObserve(localPlayer: PlayerInfo): Flow<List<PlayerInfo>>
    suspend fun leave()
}