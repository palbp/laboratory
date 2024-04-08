package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.ui.common.GameItem
import palbp.laboratory.simplexludum.ui.common.GameListSelector
import palbp.laboratory.simplexludum.ui.common.stringResource
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

// Resource identifiers
const val HOME_TAB_TITLE: String = "home_tab_title"
const val LATEST_TITLE: String = "latest_title"
const val SEE_ALL: String = "see_all"

/**
 * The View in the Model-View-ViewModel pattern for the Home tab screen
 * @param lists The list of game lists to be displayed
 * @param latest The list of latest games to be displayed
 * @param onOpenGameListIntent The function to be called when the user selects a game list
 * @param onOpenGameDetailsIntent The function to be called when the user selects a game
 */
@Composable
fun HomeTabView(
    lists: List<GameListSummary>,
    latest: List<Game>,
    onOpenGameListIntent: (GameListSummary) -> Unit,
    onOpenGameDetailsIntent: (Game) -> Unit,
) {
    SimplexLudumTheme {

        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = stringResource(HOME_TAB_TITLE),
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
                    GameItem(game = game, onGameItemSelected = onOpenGameDetailsIntent)
                }
            }
        }
    }
}
