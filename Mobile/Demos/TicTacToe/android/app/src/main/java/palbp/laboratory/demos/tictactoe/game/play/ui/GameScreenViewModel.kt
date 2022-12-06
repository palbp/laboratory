package palbp.laboratory.demos.tictactoe.game.play.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo
import palbp.laboratory.demos.tictactoe.game.play.domain.Match

/**
 * View model for the Game Screen hosted by [GameActivity].
 */
class GameScreenViewModel(val match: Match) : ViewModel() {

    private var _started by mutableStateOf(false)
    val started: Boolean = _started

    fun startMatch(localPlayer: PlayerInfo, challenge: Challenge): Job? =
        if (!started) {
            _started = true
            viewModelScope.launch {
                match.start(localPlayer, challenge)
            }
        }
        else null

    fun forfeit(): Job? = null
}