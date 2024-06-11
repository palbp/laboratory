package palbp.laboratory.simplex.ui.mycollection.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The route for the home tab.
 */
const val homeTabRoute = "home"

/**
 * The home tab.
 * @param modifier The modifier to be applied to this tab's root content.
 * @param viewModel The view model for the home tab. Passed as a parameter to enable testing.
 */
@Composable
fun HomeTab(
    modifier: Modifier = Modifier,
    viewModel: HomeTabViewModel = viewModel()
) {
    LaunchedEffect(viewModel.state) {
        if (viewModel.state is HomeTabScreenState.Idle) {
            viewModel.fetchData()
        }
    }

    val gameLists = (viewModel.state as? HomeTabScreenState.Loaded)?.lists ?: emptyList()
    val latestGames = (viewModel.state as? HomeTabScreenState.Loaded)?.latest ?: emptyList()

    HomeTabView(
        modifier = modifier,
        lists = gameLists,
        latest = latestGames,
        onOpenGameListIntent = { TODO() },
        onOpenGameDetailsIntent = { TODO() }
    )
}