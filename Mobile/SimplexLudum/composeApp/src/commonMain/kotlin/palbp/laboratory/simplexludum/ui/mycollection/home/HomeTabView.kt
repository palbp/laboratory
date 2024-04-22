package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.ui.common.GameItem
import palbp.laboratory.simplexludum.ui.common.GameListSelector
import palbp.laboratory.simplexludum.ui.common.stringResource
import palbp.laboratory.simplexludum.ui.mycollection.ContentPadding

// Resource identifiers
const val HOME_TITLE: String = "home_title"
const val HOME_LATEST_TITLE: String = "latest_title"
const val HOME_SEE_ALL_LABEL: String = "see_all"

// Tag used to identify the Home tab content
const val HOME_TAG = "home"

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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ContentPadding.current)
            .padding(horizontal = 16.dp)
            .testTag(HOME_TAG)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 32.dp),
            text = stringResource(HOME_TITLE),
            style = MaterialTheme.typography.headlineLarge
        )
        lists.forEachIndexed { index, it ->
            GameListSelector(listInfo = it, onGameListSelected = onOpenGameListIntent)
            if (index < lists.size - 1)
                Divider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text(
                text = stringResource(HOME_LATEST_TITLE),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(HOME_SEE_ALL_LABEL),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        LazyColumn {
            items(latest) { game ->
                GameItem(game = game, onGameItemSelected = onOpenGameDetailsIntent)
            }
        }
    }
}
