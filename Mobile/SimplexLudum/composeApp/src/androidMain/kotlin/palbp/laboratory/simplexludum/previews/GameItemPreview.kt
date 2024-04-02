package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.common.GameItem
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    SimplexLudumTheme {
        GameItem(
            game = Game(
                name = "The Witcher 3: Wild Hunt",
                developer = "CD Projekt Red",
                genres = setOf(Genre.ACTION, Genre.RPG, Genre.ADVENTURE),
                platform = Platform.PS4,
                distribution = Distribution.PHYSICAL
            ),
            onGameItemSelected = { }
        )
    }
}
