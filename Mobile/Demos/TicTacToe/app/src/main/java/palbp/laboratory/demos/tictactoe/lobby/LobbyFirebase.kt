package palbp.laboratory.demos.tictactoe.lobby

import kotlinx.coroutines.flow.Flow

class LobbyFirebase() : Lobby {

    override suspend fun getPlayers(): List<PlayerInfo> {
        TODO("Not yet implemented")
    }

    override fun enter(localPlayer: PlayerInfo): Flow<List<PlayerInfo>> {
        TODO("Not yet implemented")
    }

    override fun leave() {
        TODO("Not yet implemented")
    }
}