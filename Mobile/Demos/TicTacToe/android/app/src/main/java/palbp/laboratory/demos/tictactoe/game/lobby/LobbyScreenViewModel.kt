package palbp.laboratory.demos.tictactoe.game.lobby

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

    fun enterLobby(): Job? =
        if (lobbyMonitor == null) {
            val localPlayer = PlayerInfo(checkNotNull(userInfoRepo.userInfo))
            println(this.hashCode())
            lobbyMonitor = viewModelScope.launch {
                lobby.enterAndObserve(localPlayer).collect { currentPlayers ->
                    _players.value = currentPlayers.filterNot {
                        it.id != localPlayer.id
                    }
                }
            }
            lobbyMonitor
        } else null

    fun leaveLobby(): Job? = if (lobbyMonitor != null) {
        viewModelScope.launch {
            lobbyMonitor?.cancel()
            lobbyMonitor = null
            lobby.leave()
        }
    } else null
}