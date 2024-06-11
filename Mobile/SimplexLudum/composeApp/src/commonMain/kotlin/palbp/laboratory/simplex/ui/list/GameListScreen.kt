package palbp.laboratory.simplexludum.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import palbp.laboratory.simplex.ui.list.GameListScreenModel
import palbp.laboratory.simplex.ui.list.GameListScreenState
import palbp.laboratory.simplex.ui.list.GameListView

/**
 * The screen used to display a list of games.
 * The screen's view-model is injected to enable testing. Its default value is
 * the view-model used in production.
 *
 * @param listTitle the title of the list
 * @param viewModel the screen's view-model
 */
@Composable
fun GameListScreen(
    listTitle: String,
    viewModel: GameListScreenModel = viewModel()
) {
    LaunchedEffect(viewModel.state) {
        if (viewModel.state is GameListScreenState.Idle) {
            viewModel.fetchFilteredGameList(query = listTitle)
        }
    }

    GameListView(
        listTitle = listTitle,
        games = viewModel.getGames(),
        onSearchIntent = { query -> viewModel.fetchFilteredGameList(query) },
        onOpenGameDetailsIntent = { },
        onNavigateBackIntent = { }
    )

}

/**
 * Helper extension function to get the list of games from the screen's view-model.
 */
private fun GameListScreenModel.getGames() = (this.state as? GameListScreenState.Loaded)?.games ?: emptyList()