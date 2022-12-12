package palbp.laboratory.demos.tictactoe.game.play.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.parcelize.Parcelize
import palbp.laboratory.demos.tictactoe.DependenciesContainer
import palbp.laboratory.demos.tictactoe.R
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo
import palbp.laboratory.demos.tictactoe.game.play.domain.Coordinate
import palbp.laboratory.demos.tictactoe.preferences.domain.UserInfo
import palbp.laboratory.demos.tictactoe.utils.viewModelInit
import java.util.*

/**
 * Hosts the screen where the game is played.
 */
class GameActivity: ComponentActivity() {

    private val viewModel by viewModels<GameScreenViewModel> {
        viewModelInit {
            val app = (application as DependenciesContainer)
            GameScreenViewModel(app.match)
        }
    }

    companion object {
        const val MATCH_INFO_EXTRA = "MATCH_INFO_EXTRA"
        fun navigate(origin: Context, localPlayer: PlayerInfo, challenge: Challenge) {
            with(origin) {
                startActivity(
                    Intent(this, GameActivity::class.java).also {
                        it.putExtra(MATCH_INFO_EXTRA, MatchInfo(localPlayer, challenge))
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val game by viewModel.onGoingGame.collectAsState()
            val title = when (viewModel.state) {
                MatchState.STARTING -> R.string.game_screen_waiting
                MatchState.IDLE -> R.string.game_screen_waiting
                else -> null
            }

            val onMoveRequested: (at: Coordinate) -> Unit =
                if (game.localPlayerMarker == game.board.turn) {
                    { at -> viewModel.makeMove(at) }
                }
                else { { } }

            GameScreen(
                state = GameScreenState(title, game),
                onMoveRequested = onMoveRequested
            )
            if (viewModel.state == MatchState.STARTING) {
                StartingMatchDialog()
            }
        }

        if (viewModel.state == MatchState.IDLE)
            viewModel.startMatch(localPlayer, challenge)
    }

    @Suppress("deprecation")
    private val matchInfo: MatchInfo by lazy {
        val info =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                intent.getParcelableExtra(MATCH_INFO_EXTRA, MatchInfo::class.java)
            else
                intent.getParcelableExtra(MATCH_INFO_EXTRA)

        checkNotNull(info)
    }

    private val localPlayer: PlayerInfo by lazy {
        PlayerInfo(
            info = UserInfo(matchInfo.localPlayerNick),
            id = UUID.fromString(matchInfo.localPlayerId)
        )
    }

    private val challenge: Challenge by lazy {
        val opponent = PlayerInfo(
            info = UserInfo(matchInfo.opponentNick),
            id = UUID.fromString(matchInfo.opponentId)
        )

        if (localPlayer.id.toString() == matchInfo.challengerId)
            Challenge(challenger = localPlayer, challenged = opponent)
        else
            Challenge(challenger = opponent, challenged = localPlayer)
    }
}

@Parcelize
private data class MatchInfo(
    val localPlayerId: String,
    val localPlayerNick: String,
    val opponentId: String,
    val opponentNick: String,
    val challengerId: String,
) : Parcelable

private fun MatchInfo(localPlayer: PlayerInfo, challenge: Challenge): MatchInfo {
    val opponent =
        if (localPlayer == challenge.challenged) challenge.challenger
        else challenge.challenged

    return MatchInfo(
        localPlayerId = localPlayer.id.toString(),
        localPlayerNick = localPlayer.info.nick,
        opponentId = opponent.id.toString(),
        opponentNick = opponent.info.nick,
        challengerId = challenge.challenger.id.toString(),
    )
}
