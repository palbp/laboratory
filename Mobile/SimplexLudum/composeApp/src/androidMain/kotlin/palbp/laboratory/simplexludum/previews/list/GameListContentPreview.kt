package palbp.laboratory.simplexludum.previews.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.list.GameListView

@Preview(showBackground = true, showSystemUi = true, name = "Game List")
@Composable
fun GameListContentPreview() {
    val games = listOf(
        Game(
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "name2",
            developer = "developer1",
            genres = setOf(Genre.ACTION),
            platform = Platform.PS5,
            distribution = Distribution.SUBSCRIPTION
        ),
    )

    SimplexLudumTheme {
        GameListView(
            listTitle = "Backlog",
            games = games,
            onSearchIntent = { },
            onOpenGameDetailsIntent = { },
            onNavigateBackIntent = { }
        )
    }
}