package palbp.laboratory.simplexludum.ui.mycollection.find

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

// Tag used to identify the Find Games tab content
// TODO: Move this definition to the view
const val FIND_GAMES_TAG = "find"

/**
 * The actual implementation of the content of the Find tab for the MyCollection screen.
 * @param tabIndex The index of the tab in the tab navigator
 */
class FindGamesTabScreen(private val tabIndex: UInt) : Tab {
    @Composable
    override fun Content() {
        // TODO: Implement
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = tabIndex.toUShort(),
                title = "Search",
                icon = rememberVectorPainter(Icons.Outlined.Search)
            )
        }
}
