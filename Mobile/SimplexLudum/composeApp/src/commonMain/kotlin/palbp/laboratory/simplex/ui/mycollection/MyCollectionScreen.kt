package palbp.laboratory.simplex.ui.mycollection

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.stringResource
import palbp.laboratory.simplex.ui.mycollection.explore.ExploreLibraryTab
import palbp.laboratory.simplex.ui.mycollection.explore.exploreLibraryTabRoute
import palbp.laboratory.simplex.ui.mycollection.find.FindGamesTab
import palbp.laboratory.simplex.ui.mycollection.find.findGamesTabRoute
import palbp.laboratory.simplex.ui.mycollection.home.HomeTab
import palbp.laboratory.simplex.ui.mycollection.home.homeTabRoute
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.explore_tab
import simplexludum.composeapp.generated.resources.find_tab
import simplexludum.composeapp.generated.resources.home_tab

/**
 * The My Collection screen route.
 */
const val myCollectionRoute = "myCollection"

/**
 * The screen used to interact with My Collection.
 */
@Composable
fun MyCollectionScreen() {
    val tabNavigator = rememberNavController()
    val currentTab = remember { mutableStateOf(TabInfo.Home) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                TabInfo.entries.forEach { tab ->
                    TabNavigationItem(
                        tab = tab,
                        currentRoute = currentTab.value.route,
                        onTabSelected = {
                            currentTab.value = tab
                            tabNavigator.navigate(tab.route) {
                                popUpTo(myCollectionRoute) { inclusive = false }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = tabNavigator, startDestination = myCollectionRoute) {
            navigation(startDestination = TabInfo.Home.route, route = myCollectionRoute) {
                composable(route = TabInfo.Home.route) {
                    HomeTab(modifier = Modifier.padding(innerPadding))
                }
                composable(route = TabInfo.Find.route) {
                    FindGamesTab(modifier = Modifier.padding(innerPadding))
                }
                composable(route = TabInfo.Explore.route) {
                    ExploreLibraryTab(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Enum class representing the tabs in the MyCollection screen.
private enum class TabInfo(
    val route: String,
    val label: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
) {
    Home(
        route = homeTabRoute,
        label = { Text(text = stringResource(Res.string.home_tab)) },
        icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home") }
    ),
    Find(
        route = findGamesTabRoute,
        label = { Text(text = stringResource(Res.string.find_tab)) },
        icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Find") }
    ),
    Explore(
        route = exploreLibraryTabRoute,
        label = { Text(text = stringResource(Res.string.explore_tab)) },
        icon = { Icon(imageVector = Icons.Outlined.Bookmarks, contentDescription = "Explore") }
    ),
}

/**
 * Helper function used create tab on the navigation bar.
 */
@Composable
private fun RowScope.TabNavigationItem(
    tab: TabInfo,
    currentRoute: String,
    onTabSelected: () -> Unit
) {
    NavigationBarItem(
        selected = currentRoute == tab.route,
        onClick = onTabSelected,
        icon = tab.icon,
        label = tab.label,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
