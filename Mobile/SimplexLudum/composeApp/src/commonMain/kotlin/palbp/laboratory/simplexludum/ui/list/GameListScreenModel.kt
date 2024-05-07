package palbp.laboratory.simplexludum.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GetGameListWithQuery

/**
 * Sum type representing the possible states of the Game List screen
 */
sealed interface GameListScreenState {
    data object Idle : GameListScreenState
    data class Loading(val job: Job, val currentGames: List<Game>? = null) : GameListScreenState
    data class Loaded(val games: List<Game>) : GameListScreenState
}

/**
 * The view-model for the Game List screen
 * @param getGameList The function to be called to get the game list to be displayed
 */
class GameListScreenModel(
    private val getGameList: GetGameListWithQuery
) : ScreenModel {

    /**
     * The current state of the screen
     */
    var state: GameListScreenState by mutableStateOf(GameListScreenState.Idle)
        private set

    /**
     * Asynchronously fetches the game list to be displayed on the screen.
     * The result is stored in the [state] property.
     * @param query The query to filter the game list with
     * @return The job that is fetching the data
     */
    fun fetchFilteredGameList(query: String): Job =
        state.let {
            if (it is GameListScreenState.Loading) {
                it.job.cancel()
            }

            val job = screenModelScope.launch {
                // TODO: Handle errors
                val games = getGameList(query)
                if (isActive) state = GameListScreenState.Loaded(games)
            }

            state = GameListScreenState.Loading(
                job = job,
                currentGames = when (it) {
                    is GameListScreenState.Loaded -> it.games
                    is GameListScreenState.Loading -> it.currentGames
                    else -> null
                }
            )

            job
        }
}