package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.ui.MyCollectionScreen

@Preview(showBackground = true, showSystemUi = true, name = "My Collection")
@Composable
fun MyCollectionScreenPreview() {
    val gameLists = listOf(
        GameListSummary("Platinum", 19),
        GameListSummary("Completed", 36),
        GameListSummary("Backlog", 23),
        GameListSummary("Wishlist", 10),
        GameListSummary("Collections", 3)
    )
    MyCollectionScreen(
        lists = gameLists,
        onOpenGameListIntent = { },
        onOpenGameDetailsIntent = { }
    )
}
