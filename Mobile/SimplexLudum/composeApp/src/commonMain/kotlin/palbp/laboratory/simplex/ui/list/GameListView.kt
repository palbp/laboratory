package palbp.laboratory.simplex.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.ui.common.GameItem
import palbp.laboratory.simplex.ui.common.SearchBox

// Tags used to identify semantically relevant parts of the UI
const val BACK_BUTTON_TAG = "back_button"
const val LIST_TITLE_TAG = "list_title"

/**
 * The View in the Model-View-ViewModel pattern for the Game List screen
 * @param listTitle The title of the list
 * @param games The list of games to be displayed
 * @param onSearchIntent The function to be called when the user searches for a game
 * @param onOpenGameDetailsIntent The function to be called when the user selects a game
 * @param onNavigateBackIntent The function to be called when the user navigates back
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListView(
    listTitle: String,
    games: List<Game>,
    onSearchIntent: (String) -> Unit,
    onOpenGameDetailsIntent: (Game) -> Unit,
    onNavigateBackIntent: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "Navigate back",
                        modifier = Modifier
                            .fillMaxHeight()
                            .clickable { onNavigateBackIntent() }
                            .testTag(BACK_BUTTON_TAG)
                    )
                },
                title = { Text(text = listTitle, modifier = Modifier.testTag(LIST_TITLE_TAG)) }
            )
        }
    ) { paddingValues ->
        Column {
            SearchBox(onSearchRequested = onSearchIntent)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(games) { game ->
                GameItem(game = game, onGameItemSelected = onOpenGameDetailsIntent)
            }
        }
    }
}