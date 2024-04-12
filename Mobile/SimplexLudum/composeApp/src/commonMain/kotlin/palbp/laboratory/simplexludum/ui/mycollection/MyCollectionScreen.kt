package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme
import palbp.laboratory.simplexludum.ui.mycollection.explore.ExploreLibraryTabScreen
import palbp.laboratory.simplexludum.ui.mycollection.find.FindGamesTabScreen
import palbp.laboratory.simplexludum.ui.mycollection.home.HomeTabScreen

// Tags used to identify semantically relevant parts of the UI, in this case,
// the tabs in the navigation bar
const val HOME_TAB_TAG = "home_tab"
const val EXPLORE_LIBRARY_TAB_TAG = "explore_tab"
const val FIND_GAMES_TAB_TAG = "find_tab"

/**
 * The screen that represents the My Collection screen in the application. The screen
 * supports bottom navigation with three tabs: Home, Explore, and Find. Each tab
 * is represented by a different screen, with its own view, view-model and navigation logic.
 */
class MyCollectionScreen : Screen {

    private val homeTabScreen by lazy { HomeTabScreen(tabIndex = 0u) }
    private val exploreTabScreen by lazy { ExploreLibraryTabScreen(tabIndex = 1u) }
    private val findTabScreen by lazy { FindGamesTabScreen(tabIndex = 2u) }

    @Composable
    override fun Content() {
        SimplexLudumTheme {
            TabNavigator(
                tab = homeTabScreen
            ) { tabNavigator ->
                Scaffold(
                    content = { paddingValues -> TabContentHolder(paddingValues) { CurrentTab() } },
                    bottomBar = {
                        NavigationBar {
                            TabNavigationItem(
                                tab = homeTabScreen,
                                onTabSelected = { tabNavigator.current = homeTabScreen },
                                testTag = HOME_TAB_TAG
                            )
                            TabNavigationItem(
                                tab = exploreTabScreen,
                                onTabSelected = { tabNavigator.current = exploreTabScreen },
                                testTag = EXPLORE_LIBRARY_TAB_TAG
                            )
                            TabNavigationItem(
                                tab = findTabScreen,
                                onTabSelected = { tabNavigator.current = findTabScreen },
                                testTag = FIND_GAMES_TAB_TAG
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(
    tab: Tab,
    onTabSelected: () -> Unit,
    testTag: String
) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = onTabSelected,
        icon = {
            tab.options.icon?.let {
                Icon(it, contentDescription = tab.options.title)
            }
        },
        label = { Text(tab.options.title) },
        modifier = Modifier.testTag(testTag)
    )
}

