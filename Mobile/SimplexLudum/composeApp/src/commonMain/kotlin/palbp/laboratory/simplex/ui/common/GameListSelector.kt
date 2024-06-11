package palbp.laboratory.simplex.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import palbp.laboratory.simplex.domain.GameListSummary

private const val GAME_LIST_SELECTOR_TAG = "game-list-selector"
private const val GAME_LIST_SELECTOR_NAV_ICON_TAG = "game-list-selector-nav-icon"

/**
 * Computes the test tag for the game list selector.
 */
fun computeGameListSelectorTag(listInfo: GameListSummary) = "$GAME_LIST_SELECTOR_TAG-${listInfo.name}"

/**
 * Computes the test tag for the game list selector navigation icon.
 */
fun computeGameListSelectorNavIconTag(listInfo: GameListSummary) = "$GAME_LIST_SELECTOR_NAV_ICON_TAG-${listInfo.name}"

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
            .testTag(computeGameListSelectorTag(listInfo))
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
            imageVector = Icons.AutoMirrored.Outlined.NavigateNext,
            contentDescription = "open list icon",
            modifier = Modifier.testTag(computeGameListSelectorNavIconTag(listInfo))
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
        "Wish List" -> Icons.Default.FavoriteBorder
        "Collections" -> Icons.AutoMirrored.Filled.LibraryBooks
        "Completed" -> Icons.Default.TaskAlt
        "Backlog" -> Icons.AutoMirrored.Filled.FormatListBulleted
        else -> Icons.AutoMirrored.Filled.KeyboardArrowRight
    }
