package palbp.laboratory.demos.tictactoe.game.play.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kotlinx.parcelize.Parcelize
import palbp.laboratory.demos.tictactoe.DependenciesContainer
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo
import palbp.laboratory.demos.tictactoe.game.play.domain.Board
import palbp.laboratory.demos.tictactoe.game.play.domain.Game
import palbp.laboratory.demos.tictactoe.game.play.domain.Marker
import palbp.laboratory.demos.tictactoe.utils.viewModelInit

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
        private const val MATCH_INFO_EXTRA = "MATCH_INFO_EXTRA"
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
            val starting = !viewModel.started
            val game = Game(Marker.CROSS, Board())
            GameScreen(state = GameScreenState(starting, game))

            if (starting) {
                StartingMatchDialog()
            }
        }
    }
}

@Parcelize
private data class MatchInfo(
    val localPlayerId: String,
    val challengerId: String,
    val opponentId: String,
    val opponentNick: String
) : Parcelable

private fun MatchInfo(localPlayer: PlayerInfo, challenge: Challenge): MatchInfo {
    val opponent =
        if (localPlayer == challenge.challenged) challenge.challenger
        else challenge.challenged

    return MatchInfo(
        localPlayerId = localPlayer.id.toString(),
        challengerId = challenge.challenger.id.toString(),
        opponentId = opponent.id.toString(),
        opponentNick = opponent.info.nick
    )
}
