package palbp.laboratory.simplexludum.ui.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GetGameListWithQuery
import palbp.laboratory.simplexludum.infrastructure.fakeGetGameList
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

/**
 * The screen used to display a list of games.
 * The screen's view-model is injected to enable testing. the parameter's
 * default value is the view-model used in production.
 *
 * @param listTitle the title of the list
 * @param getGameList the function used to get the list of games to be displayed
 * @param screenModel the screen's view-model
 */
class GameListScreen(
    private val listTitle: String,
    private val getGameList: GetGameListWithQuery = ::fakeGetGameList,
    private val screenModel: GameListScreenModel = GameListScreenModel(getGameList)
) : Screen {

    @Composable
    override fun Content() {
        SimplexLudumTheme {
            LifecycleEffect(
                onStarted = {
                    if (screenModel.state is GameListScreenState.Idle) {
                        screenModel.fetchFilteredGameList(query = listTitle)
                    }
                }
            )

            GameListView(
                listTitle = listTitle,
                games = getGames(),
                onSearchIntent = ::refineSearch,
                onOpenGameDetailsIntent = { },
                onNavigateBackIntent = { }
            )
        }
    }

    private fun getGames(): List<Game> =
        (screenModel.state as? GameListScreenState.Loaded)?.games ?: emptyList()

    private fun refineSearch(query: String) {
        screenModel.fetchFilteredGameList(query)
    }
}

