package palbp.laboratory.demos.navigation

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import palbp.laboratory.demos.navigation.ui.theme.NavigationDemoTheme

const val tabbedScreenRoute = "tabbed"

@Composable
fun TabbedScreen(
    onBackIntent: () -> Unit,
    viewModel: TabbedScreenViewModel = viewModel()
) {
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
                                popUpTo(tabbedScreenRoute) {
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = tabNavigator, startDestination = tabbedScreenRoute) {
            navigation(startDestination = TabInfo.Home.route, route = tabbedScreenRoute) {
                composable(route = TabInfo.Home.route) {
                    HomeTab(modifier = Modifier.padding(innerPadding))
                }
                composable(route = TabInfo.Find.route) {
                    FindTab(modifier = Modifier.padding(innerPadding))
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
            label = { Text(text = "Home") },
            icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home") }
        ),
        Find(
            route = findTabRoute,
            label = { Text(text = "Find") },
            icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Find") }
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

@Preview(showBackground = true)
@Composable
fun TabbedScreenPreview() {
    NavigationDemoTheme {
        TabbedScreen(onBackIntent = {})
    }
}

class TabbedScreenViewModel : ViewModel() {
    init {
        Log.v(TAG, "TabbedScreenViewModel init ${hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "TabbedScreenViewModel.onCleared() ${hashCode()}")
    }
}