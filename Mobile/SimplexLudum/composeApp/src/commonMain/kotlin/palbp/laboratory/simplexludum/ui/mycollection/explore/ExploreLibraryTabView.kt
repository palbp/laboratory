package palbp.laboratory.simplexludum.ui.mycollection.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplexludum.ui.mycollection.ContentPadding

// Tag used to identify the Explore Library tab content
const val EXPLORE_LIBRARY_TAG = "find"

/**
 * The View in the Model-View-ViewModel pattern for the Explore Library tab screen
 */
@Composable
fun ExploreLibraryTabView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ContentPadding.current)
            .padding(horizontal = 16.dp)
            .testTag(EXPLORE_LIBRARY_TAG)
    ) {
        // TODO
    }
}