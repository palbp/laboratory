package palbp.laboratory.simplexludum.ui.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.infrastructure.getFakeGameListWithQuery
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

/**
 * The screen used to display a list of games.
 * The screen's view-model is injected to enable testing. the parameter's
 * default value is the view-model used in production.
 */
class GameListScreen(
    private val listTitle: String,
    private val screenModel: GameListScreenModel = GameListScreenModel(
        getGameList = ::getFakeGameListWithQuery
    )
) : Screen {

    @Composable
    override fun Content() {
        SimplexLudumTheme {
            LifecycleEffect(
                onStarted = {
                    if (screenModel.state is GameListScreenState.Idle) {
                        screenModel.fetchFilteredGameList()
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

