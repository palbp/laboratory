package palbp.laboratory.demos.tictactoe.lobby

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import palbp.laboratory.demos.tictactoe.DependenciesContainer
import palbp.laboratory.demos.tictactoe.game.GameActivity
import palbp.laboratory.demos.tictactoe.preferences.PreferencesActivity
import palbp.laboratory.demos.tictactoe.utils.viewModelInit

/**
 * The screen used to display the list of players in the lobby, that is, available to play.
 * This a reactive version of this screen. An alternative approach is used in
 * [LobbyActivityReactive].
 *
 * TODO: Remove all traces of the pull-based approach after the next class
 */
class LobbyActivity : ComponentActivity() {

    private val viewModelPull by viewModels<LobbyScreenViewModelPull> {
        viewModelInit {
            val lobby = (application as DependenciesContainer).lobbyPull
            LobbyScreenViewModelPull(lobby)
        }
    }

    private val viewModel by viewModels<LobbyScreenViewModel> {
        viewModelInit {
            val lobby = (application as DependenciesContainer).lobby
            LobbyScreenViewModel(lobby)
        }
    }

    companion object {
        fun navigate(context: Context) {
            with(context) {
                val intent = Intent(this, LobbyActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            val players = viewModelPull.players
            val players by viewModel.players.collectAsState()
            LobbyScreen(
                state = LobbyScreenState(players),
                onPlayerSelected = {
                    GameActivity.navigate(this)
                },
                onBackRequested = { finish() },
                onPreferencesRequested = {
                    PreferencesActivity.navigate(this, finishOnSave = true)
                }
            )
        }

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
//                viewModelPull.enterLobby()
                viewModel.enterLobby()
            }

            override fun onStop(owner: LifecycleOwner) {
//                viewModelPull.leaveLobby()
                viewModel.leaveLobby()
            }
        })
    }
}