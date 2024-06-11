package palbp.laboratory.simplex.ui.mycollection.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

// Tag used to identify the Explore Library tab content
const val EXPLORE_LIBRARY_TAG = "find"

/**
 * The View in the Model-View-ViewModel pattern for the Explore Library tab screen
 * @param modifier The modifier to be applied to this tab content
 */
@Composable
fun ExploreLibraryTabView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag(EXPLORE_LIBRARY_TAG)
    ) {
        // TODO
    }
}