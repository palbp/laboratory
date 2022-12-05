package palbp.laboratory.demos.tictactoe.game.play.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import palbp.laboratory.demos.tictactoe.game.play.model.Board
import palbp.laboratory.demos.tictactoe.game.play.model.Game
import palbp.laboratory.demos.tictactoe.game.play.model.Marker

/**
 * Hosts the screen where the game is played.
 */
class GameActivity: ComponentActivity() {
    companion object {
        private const val OPPONENT_ID_EXTRA = "OPPONENT_ID_EXTRA"
        fun navigate(origin: Context, opponentId: String) {
            with(origin) {
                startActivity(
                    Intent(this, GameActivity::class.java).also {
                        it.putExtra(OPPONENT_ID_EXTRA, opponentId)
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val game = Game(Marker.CROSS, Board())
            GameScreen(state = GameScreenState(game))
        }
    }
}
