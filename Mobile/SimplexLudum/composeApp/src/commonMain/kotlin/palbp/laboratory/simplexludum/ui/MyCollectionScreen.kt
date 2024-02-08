package palbp.laboratory.simplexludum.ui

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
import cafe.adriel.voyager.core.screen.Screen
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.stringResource
import palbp.laboratory.simplexludum.ui.theme.SimplexLudumTheme

// Resource identifiers
const val MY_COLLECTION_TITLE: String = "my_collection_title"
const val LATEST_TITLE: String = "latest_title"
const val SEE_ALL: String = "see_all"

@Composable
fun MyCollectionScreen(
    lists: List<GameListSummary>,
    onOpenGameListIntent: (GameListSummary) -> Unit,
    onOpenGameDetailsIntent: (Game) -> Unit
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
        }
    }
}

object MyCollectionScreen : Screen {
    @Composable
    override fun Content() {
        MyCollectionScreen(
            lists = emptyList(),
            onOpenGameListIntent = { /* TODO */ },
            onOpenGameDetailsIntent = { /* TODO */ }
        )
    }
}