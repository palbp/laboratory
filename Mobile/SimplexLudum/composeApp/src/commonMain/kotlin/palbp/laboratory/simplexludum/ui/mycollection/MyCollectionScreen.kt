package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.infrastructure.getFakeGameLists
import palbp.laboratory.simplexludum.infrastructure.getFakeLatestGames

// Resource identifiers
const val MY_COLLECTION_TITLE: String = "my_collection_title"
const val LATEST_TITLE: String = "latest_title"
const val SEE_ALL: String = "see_all"

/**
 * The actual screen implementation, which contains the screen's navigation and
 * is responsible for fetching the screen data and passing it to the view.
 * The screen's view-model is injected to enable testing.
 * @param screenModel The view-model for the screen
 */
class MyCollectionScreen(
    private val screenModel: MyCollectionScreenModel = MyCollectionScreenModel(
        getGameLists = ::getFakeGameLists,
        getLatestGames = ::getFakeLatestGames
    )
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<MyCollectionScreenModel> { screenModel }

        LaunchedEffect(screenModel.state) {
            if (screenModel.state is ScreenState.Idle) {
                screenModel.fetchScreenData()
            }
        }

        val gameLists = (screenModel.state as? ScreenState.Loaded)?.lists ?: emptyList()
        val latestGames = (screenModel.state as? ScreenState.Loaded)?.latest ?: emptyList()

        MyCollectionView(
            lists = gameLists,
            latest = latestGames,
            onOpenGameListIntent = { TODO() },
            onOpenGameDetailsIntent = { TODO() }
        )
    }
}
