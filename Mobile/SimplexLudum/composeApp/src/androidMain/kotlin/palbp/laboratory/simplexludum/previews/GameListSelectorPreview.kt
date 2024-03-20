package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.ui.common.GameListSelector
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

@Preview(showBackground = true)
@Composable
fun GameListSelectorPreview() {
    SimplexLudumTheme {
        GameListSelector(
            listInfo = GameListSummary("Platined", 3),
            onGameListSelected = { },
        )
    }
}
