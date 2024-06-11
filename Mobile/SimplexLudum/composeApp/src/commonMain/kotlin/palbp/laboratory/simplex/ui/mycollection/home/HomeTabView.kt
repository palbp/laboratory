package palbp.laboratory.simplex.ui.mycollection.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.GameListSummary
import palbp.laboratory.simplex.ui.common.GameItem
import palbp.laboratory.simplex.ui.common.GameListSelector
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.home_title
import simplexludum.composeapp.generated.resources.latest_title
import simplexludum.composeapp.generated.resources.see_all_title

// Tag used to identify the Home tab content
const val HOME_TAG = "home"

/**
 * The View in the Model-View-ViewModel pattern for the Home tab screen
 * @param modifier The modifier to be applied to this tab content
 * @param lists The list of game lists to be displayed
 * @param latest The list of latest games to be displayed
 * @param onOpenGameListIntent The function to be called when the user selects a game list
 * @param onOpenGameDetailsIntent The function to be called when the user selects a game
 */
@Composable
fun HomeTabView(
    modifier: Modifier = Modifier,
    lists: List<GameListSummary>,
    latest: List<Game>,
    onOpenGameListIntent: (GameListSummary) -> Unit,
    onOpenGameDetailsIntent: (Game) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag(HOME_TAG)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(Res.string.home_title),
            style = MaterialTheme.typography.headlineLarge
        )
        lists.forEachIndexed { index, it ->
            GameListSelector(listInfo = it, onGameListSelected = onOpenGameListIntent)
            if (index < lists.size - 1)
                HorizontalDivider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(Res.string.latest_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(Res.string.see_all_title),
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
