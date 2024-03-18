package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.infrastructure.getFakeGameLists
import palbp.laboratory.simplexludum.infrastructure.getFakeLatestGames
import palbp.laboratory.simplexludum.ui.common.stringResource
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

// Resource identifiers
const val MY_COLLECTION_TITLE: String = "my_collection_title"
const val LATEST_TITLE: String = "latest_title"
const val SEE_ALL: String = "see_all"

/**
 * The View in the Model-View-ViewModel pattern for the MyCollection screen
 */
@Composable
fun MyCollectionScreen(
    lists: List<GameListSummary>,
    latest: List<Game>,
    onOpenGameListIntent: (GameListSummary) -> Unit,
    onOpenGameDetailsIntent: (Game) -> Unit,
) {
    SimplexLudumTheme {

        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = stringResource(MY_COLLECTION_TITLE),
                style = MaterialTheme.typography.headlineLarge
            )
            lists.dropLast(n = 1).forEach {
                GameListSelector(listInfo = it, onGameListSelected = onOpenGameListIntent)
                Divider()
            }
            lists.lastOrNull()?.let {
                GameListSelector(listInfo = it, onGameListSelected = onOpenGameListIntent)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
            ) {
                Text(text = stringResource(LATEST_TITLE), style = MaterialTheme.typography.titleMedium)
                Text(
                    text = stringResource(SEE_ALL),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column {
                latest.forEach { game ->
                    GameItem(game = game, onOpenGameDetailsIntent = onOpenGameDetailsIntent)
                }
            }
        }
    }
}

/**
 * Implementation of the Voyager navigation contract
 */
object MyCollectionScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<MyCollectionScreenModel> {
            MyCollectionScreenModel(
                getGamesList = ::getFakeGameLists,
                getLatestGames = ::getFakeLatestGames
            )
        }

        LaunchedEffect(screenModel.state) {
            if (screenModel.state is ScreenState.Idle) {
                screenModel.fetchScreenData()
            }
        }

        val gameLists = (screenModel.state as? ScreenState.Loaded)?.lists ?: emptyList()
        val latestGames = (screenModel.state as? ScreenState.Loaded)?.latest ?: emptyList()

        MyCollectionScreen(
            lists = gameLists,
            latest = latestGames,
            onOpenGameListIntent = { TODO() },
            onOpenGameDetailsIntent = { TODO() }
        )
    }
}
