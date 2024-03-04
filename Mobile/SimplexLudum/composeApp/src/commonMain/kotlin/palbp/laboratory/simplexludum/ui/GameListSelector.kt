package palbp.laboratory.simplexludum.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary

const val GAME_LIST_SELECTOR_TAG = "game-list-selector"
const val GAME_LIST_SELECTOR_NAV_ICON_TAG = "game-list-selector-nav-icon"

@Composable
fun GameListSelector(
    listInfo: GameListSummary,
    onGameListSelected: (GameListSummary) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onGameListSelected(listInfo) }
            .testTag("$GAME_LIST_SELECTOR_TAG-${listInfo.name}")
    ) {
        Icon(imageVector = listInfo.toIcon(), contentDescription = "list id icon")
        Column(
            modifier = Modifier.fillMaxWidth(0.9f).padding(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = listInfo.name.value
            )
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = "${listInfo.size} items"
            )
        }
        Icon(
            imageVector = Icons.Outlined.NavigateNext,
            contentDescription = "open list icon",
            modifier = Modifier
                .testTag("$GAME_LIST_SELECTOR_NAV_ICON_TAG-${listInfo.name}")
        )
    }
}

/**
 * Function that returns the icon for the [GameListSummary] item.
 * TODO: Resources MUST be localized, so we need to use the string resource id to get the icon.
 */
fun GameListSummary.toIcon(): ImageVector =
    when (name.toString()) {
        "Platinum" -> Icons.Outlined.EmojiEvents
        "Wishlist" -> Icons.Default.FavoriteBorder
        "Collections" -> Icons.Default.LibraryBooks
        "Completed" -> Icons.Default.TaskAlt
        "Backlog" -> Icons.Default.FormatListBulleted
        else -> Icons.Default.KeyboardArrowRight
    }
