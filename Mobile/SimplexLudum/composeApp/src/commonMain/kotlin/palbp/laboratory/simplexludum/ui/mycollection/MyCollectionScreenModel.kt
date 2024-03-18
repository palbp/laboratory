package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary

sealed interface ScreenState {
    data object Idle : ScreenState
    data object Loading : ScreenState
    data class Loaded(val lists: List<GameListSummary>, val latest: List<Game>) : ScreenState
}

/**
 * The View-model for the MyCollection screen
 */
class MyCollectionScreenModel(
    private val getGamesList: suspend () -> List<GameListSummary>,
    private val getLatestGames: suspend () -> List<Game>,
) : ScreenModel {

    var state: ScreenState by mutableStateOf(ScreenState.Idle)
        private set

    fun fetchScreenData() {
        if (state != ScreenState.Loading) {
            state = ScreenState.Loading
            screenModelScope.launch {
                // TODO: Handle errors
                val gameLists = getGamesList()
                val latestGames = getLatestGames()
                state = ScreenState.Loaded(lists = gameLists, latest = latestGames)
            }
        }
    }
}
