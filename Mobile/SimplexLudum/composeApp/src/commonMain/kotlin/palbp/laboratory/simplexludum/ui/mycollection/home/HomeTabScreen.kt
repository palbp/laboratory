package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.infrastructure.getFakeGameLists
import palbp.laboratory.simplexludum.infrastructure.getFakeLatestGames

/**
 * The actual screen implementation, which contains the screen's navigation and
 * is responsible for fetching the screen data and passing it to the view.
 * The screen's view-model is injected to enable testing.
 * @param screenModel The view-model for the screen
 */
class HomeTabScreen(
    private val screenModel: HomeTabScreenModel = HomeTabScreenModel(
        getGameLists = ::getFakeGameLists,
        getLatestGames = ::getFakeLatestGames
    )
) : Screen {

    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = {
                if (screenModel.state is HomeTabScreenState.Idle) {
                    screenModel.fetchScreenData()
                }
            }
        )

        val gameLists = (screenModel.state as? HomeTabScreenState.Loaded)?.lists ?: emptyList()
        val latestGames = (screenModel.state as? HomeTabScreenState.Loaded)?.latest ?: emptyList()

        HomeTabView(
            lists = gameLists,
            latest = latestGames,
            onOpenGameListIntent = { TODO() },
            onOpenGameDetailsIntent = { TODO() }
        )
    }
}
