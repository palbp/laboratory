package palbp.laboratory.simplex.ui.mycollection.find

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

// Tag used to identify the Find Games tab content
const val FIND_GAMES_TAG = "find"

/**
 * The View in the Model-View-ViewModel pattern for the Find Games tab screen
 * @param modifier The modifier to apply to this tab
 */
@Composable
fun FindGamesTabView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag(FIND_GAMES_TAG)
    ) {
        // TODO
    }
}
