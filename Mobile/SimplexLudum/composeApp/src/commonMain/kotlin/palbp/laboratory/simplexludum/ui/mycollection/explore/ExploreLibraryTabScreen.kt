package palbp.laboratory.simplexludum.ui.mycollection.explore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

// Tag used to identify the Explore Library tab content
// TODO: Move this definition to the view
const val EXPLORE_LIBRARY_TAG = "find"

/**
 * The actual implementation of the content of the Explore tab for the MyCollection screen.
 * @param tabIndex The index of the tab in the tab navigator
 */
class ExploreLibraryTabScreen(private val tabIndex: UInt) : Tab {
    @Composable
    override fun Content() {
        // TODO: Implement
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = tabIndex.toUShort(),
                title = "Library",
                icon = rememberVectorPainter(Icons.Outlined.Bookmarks)
            )
        }
}