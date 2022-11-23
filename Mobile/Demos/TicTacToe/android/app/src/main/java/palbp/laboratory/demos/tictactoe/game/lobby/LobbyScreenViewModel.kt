package palbp.laboratory.demos.tictactoe.game.lobby

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import palbp.laboratory.demos.tictactoe.preferences.UserInfoRepository

/**
 * View model for the Lobby Screen hosted by [LobbyActivity]
 */
class LobbyScreenViewModel(
    val lobby: Lobby,
    val userInfoRepo: UserInfoRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<PlayerInfo>>(emptyList())
    val players = _players.asStateFlow()

    private var lobbyMonitor: Job? = null
    private val userInfo by lazy { checkNotNull(userInfoRepo.userInfo) }

    fun enterLobby() {
        if (lobbyMonitor == null) {
            val localPlayer = PlayerInfo(userInfo)
            lobbyMonitor = viewModelScope.launch {
                lobby.enter(localPlayer).collect { currentPlayers ->
                    _players.value = currentPlayers.filterNot {
                        it.id == localPlayer.id
                    }
                }
            }
        }
    }

    fun leaveLobby() {
        lobbyMonitor?.cancel()
        lobbyMonitor = null
        lobby.leave()
    }
}
