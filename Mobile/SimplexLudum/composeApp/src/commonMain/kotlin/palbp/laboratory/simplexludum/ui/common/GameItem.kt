package palbp.laboratory.simplexludum.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import palbp.laboratory.simplexludum.domain.Game

private const val GAME_ITEM_BASE_TAG = "get-item"

/**
 * Computes the test tag for the game item used to display the game in the list.
 */
fun computeGameItemTag(game: Game) = "$GAME_ITEM_BASE_TAG-${game.name}"

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GameItem(game: Game, onOpenGameDetailsIntent: (Game) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onOpenGameDetailsIntent(game) }
            .testTag(computeGameItemTag(game))
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 120.dp)
    ) {
        Image(
            painter = painterResource("cover_placeholder.png"),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight(),
        )
        Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp)) {
            GameItemTitle(game = game)
            GameItemDeveloper(game = game)
            GameItemLaunchDate(game = game)
            GameItemGenres(game = game)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painter = getPlatformResourcePainter(game),
                    contentDescription = game.platform.name,
                    modifier = Modifier
                        .fillMaxHeight(0.55f)
                        .align(Alignment.Bottom)
                )
                Image(
                    painter = getDistributionResourcePainter(game),
                    contentDescription = game.distribution.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun GameItemTitle(game: Game) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = game.name.toString(),
    )
}

@Composable
private fun GameItemDeveloper(game: Game) {
    GameItemProperty(value = game.developer.toString())
}

@Composable
private fun GameItemLaunchDate(game: Game) {
    GameItemProperty(value = "April, 2016")
}

@Composable
private fun GameItemGenres(game: Game) {
    GameItemProperty(value = game.genres.joinToString(separator = " ● "))
}

@Composable
private fun GameItemProperty(value: String, modifier: Modifier = Modifier) {
    Text(
        style = MaterialTheme.typography.bodySmall,
        text = value,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier.padding(top = 4.dp)
    )
}
