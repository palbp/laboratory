package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.GetGameLists
import palbp.laboratory.simplexludum.domain.GetLatestGames

/**
 * Sum type representing the possible states of the MyCollection screen
 */
sealed interface ScreenState {
    data object Idle : ScreenState
    data object Loading : ScreenState
    data class Loaded(val lists: List<GameListSummary>, val latest: List<Game>) : ScreenState
}

/**
 * The View-model for the MyCollection screen
 * @param getGameLists The function to be called to get the game lists
 * @param getLatestGames The function to be called to get the latest games
 */
class MyCollectionScreenModel(
    private val getGameLists: GetGameLists,
    private val getLatestGames: GetLatestGames,
) : ScreenModel {

    var state: ScreenState by mutableStateOf(ScreenState.Idle)
        private set

    /**
     * Fetches the screen data.
     * @return The job that is fetching the data, or null if the screen is already loading
     */
    fun fetchScreenData(): Job? =
        if (state != ScreenState.Loading) {
            state = ScreenState.Loading
            screenModelScope.launch {
                // TODO: Handle errors
                val gameLists = getGameLists()
                val latestGames = getLatestGames()
                state = ScreenState.Loaded(lists = gameLists, latest = latestGames)
            }
        }
        else {
            null
        }
}
