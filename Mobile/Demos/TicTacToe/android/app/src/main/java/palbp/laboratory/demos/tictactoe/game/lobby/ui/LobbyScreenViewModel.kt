package palbp.laboratory.demos.tictactoe.game.lobby.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import palbp.laboratory.demos.tictactoe.game.lobby.model.*
import palbp.laboratory.demos.tictactoe.preferences.model.UserInfoRepository

/**
 * View model for the Lobby Screen hosted by [LobbyActivity]
 */
class LobbyScreenViewModel(
    val lobby: Lobby,
    val userInfoRepo: UserInfoRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<PlayerInfo>>(emptyList())
    val players = _players.asStateFlow()

    private val _pendingMatch = MutableStateFlow<MatchStarting?>(null)
    val pendingMatch = _pendingMatch.asStateFlow()

    private var lobbyMonitor: Job? = null

    fun enterLobby(): Job? =
        if (lobbyMonitor == null) {
            val localPlayer = PlayerInfo(checkNotNull(userInfoRepo.userInfo))
            lobbyMonitor = viewModelScope.launch {
                lobby.enterAndObserve(localPlayer).collect { event ->
                    when(event) {
                        is RosterUpdated -> {
                            _players.value = event.players.filter {
                                it != localPlayer
                            }
                        }
                        is ChallengeReceived -> {
                            _pendingMatch.value = IncomingChallenge(event.challenge)
                        }
                    }
                }
            }
            lobbyMonitor
        } else null

    fun leaveLobby(): Job? =
        if (lobbyMonitor != null) {
            viewModelScope.launch {
                lobbyMonitor?.cancel()
                lobbyMonitor = null
                _pendingMatch.value = null
                lobby.leave()
            }
        } else null

    fun sendChallenge(opponent: PlayerInfo): Job? =
        if (lobbyMonitor != null) {
            viewModelScope.launch {
                _pendingMatch.value = SentChallenge(lobby.issueChallenge(to = opponent))
                lobbyMonitor = null
            }
        }
        else null
}

/**
 * Sum type used to represent pending match events.
 * [IncomingChallenge] means that a match is about to start because a remote player challenged
 * the local player
 * [SentChallenge] means that a match is about to start because the local player challenged
 * another player in the lobby
 */
sealed class MatchStarting(val challenge: Challenge)
class IncomingChallenge(challenge: Challenge) : MatchStarting(challenge)
class SentChallenge(challenge: Challenge) : MatchStarting(challenge)

