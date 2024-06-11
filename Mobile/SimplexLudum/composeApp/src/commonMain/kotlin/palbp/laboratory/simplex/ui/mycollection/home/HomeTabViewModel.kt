package palbp.laboratory.simplex.ui.mycollection.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.GameListSummary
import palbp.laboratory.simplex.domain.GetGameLists
import palbp.laboratory.simplex.domain.GetLatestGames
import palbp.laboratory.simplex.infrastructure.fakeGetGameLists
import palbp.laboratory.simplex.infrastructure.fakeGetLatestGames

/**
 * Sum type representing the possible states of the Home tab of the MyCollection screen
 */
sealed interface HomeTabScreenState {
    data object Idle : HomeTabScreenState
    data object Loading : HomeTabScreenState
    data class Loaded(val lists: List<GameListSummary>, val latest: List<Game>) : HomeTabScreenState
}

/**
 * The View-model for the Home tab of the MyCollection screen
 * @param getGameLists The function to be called to get the game lists, passed as a parameter to
 * enable testing. Defaults to the production implementation.
 * @param getLatestGames The function to be called to get the latest games, passed as a parameter to
 *  * enable testing. Defaults to the production implementation.
 */
class HomeTabViewModel(
    private val getGameLists: GetGameLists = ::fakeGetGameLists,
    private val getLatestGames: GetLatestGames = ::fakeGetLatestGames,
) : ViewModel() {

    var state: HomeTabScreenState by mutableStateOf(HomeTabScreenState.Idle)
        private set

    /**
     * Fetches the tab data.
     * @return The job that is fetching the data, or null if the data is already being loaded
     */
    fun fetchData(): Job? =
        if (state != HomeTabScreenState.Loading) {
            state = HomeTabScreenState.Loading
            viewModelScope.launch {
                // TODO: Handle errors
                val gameLists = getGameLists()
                val latestGames = getLatestGames()
                state = HomeTabScreenState.Loaded(lists = gameLists, latest = latestGames)
            }
        }
        else {
            null
        }
}
