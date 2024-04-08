package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.mycollection.home.HomeTabView

@Preview(showBackground = true, showSystemUi = true, name = "My Collection")
@Composable
fun HomeTabContentPreview() {
    val gameLists = listOf(
        GameListSummary("Platinum", 19),
        GameListSummary("Completed", 36),
        GameListSummary("Backlog", 23),
        GameListSummary("Wishlist", 10),
        GameListSummary("Collections", 3)
    )
    val latest = listOf(
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
    HomeTabView(
        lists = gameLists,
        latest = latest,
        onOpenGameListIntent = { },
        onOpenGameDetailsIntent = { }
    )
}
