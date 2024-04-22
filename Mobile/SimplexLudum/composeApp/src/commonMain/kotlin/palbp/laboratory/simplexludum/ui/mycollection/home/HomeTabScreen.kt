package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import palbp.laboratory.simplexludum.infrastructure.getFakeGameLists
import palbp.laboratory.simplexludum.infrastructure.getFakeLatestGames
import palbp.laboratory.simplexludum.ui.common.stringResource

const val HOME_TAB_TITLE: String = "home_tab_title"

/**
 * The actual implementation of the content of the Home tab for the MyCollection screen.
 * The tab's view-model is injected to enable testing. The parameter's default value
 * is the view-model used in production.
 * @param tabIndex The index of the tab in the tab navigator
 */
class HomeTabScreen(
    private val tabIndex: UInt,
    private val tabModel: HomeTabScreenModel = HomeTabScreenModel(
        getGameLists = ::getFakeGameLists,
        getLatestGames = ::getFakeLatestGames
    )
) : Tab {

    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = {
                if (tabModel.state is HomeTabScreenState.Idle) {
                    tabModel.fetchScreenData()
                }
            }
        )

        val gameLists = (tabModel.state as? HomeTabScreenState.Loaded)?.lists ?: emptyList()
        val latestGames = (tabModel.state as? HomeTabScreenState.Loaded)?.latest ?: emptyList()

        HomeTabView(
            lists = gameLists,
            latest = latestGames,
            onOpenGameListIntent = { },
            onOpenGameDetailsIntent = { }
        )
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
