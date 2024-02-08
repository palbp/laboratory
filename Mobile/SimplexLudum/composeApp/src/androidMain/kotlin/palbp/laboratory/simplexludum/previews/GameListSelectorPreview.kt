package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.ui.GameListSelector

@Preview(showBackground = true)
@Composable
fun GameListSelectorPreview() {
    GameListSelector(
        listInfo = GameListSummary("Platined", 3),
        onGameListSelected = { },
    )
}
