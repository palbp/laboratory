package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import palbp.laboratory.simplexludum.infrastructure.fakeGetGameLists
import palbp.laboratory.simplexludum.infrastructure.fakeGetLatestGames
import palbp.laboratory.simplexludum.ui.common.stringResource
import palbp.laboratory.simplexludum.ui.list.GameListScreen

const val HOME_TAB_TITLE: String = "home_tab_title"

/**
 * The actual implementation of the content of the Home tab for the MyCollection screen.
 * The tab's view-model is injected to enable testing. The parameter's default value
 * is the view-model used in production.
 * @param tabIndex The index of the tab in the tab navigator
 */
class HomeTab(
    private val tabIndex: UInt,
    private val tabModel: HomeTabScreenModel = HomeTabScreenModel(
        getGameLists = ::fakeGetGameLists,
        getLatestGames = ::fakeGetLatestGames
    )
) : Tab {

    @Composable
    override fun Content() {
        Navigator(screen = HomeTabScreen(tabModel))
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = tabIndex.toUShort(),
                title = stringResource(HOME_TAB_TITLE),
                icon = rememberVectorPainter(Icons.Outlined.Home)
            )
        }
}

/**
 * Actual implementation of the screen. This is required to enable nested navigation
 * in the tab navigator.
 */
class HomeTabScreen(private val screenModel: HomeTabScreenModel) : Screen {

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

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
            onOpenGameListIntent = { navigator.push(GameListScreen(listTitle = it.name.toString())) },
            onOpenGameDetailsIntent = { }
        )
    }
}

