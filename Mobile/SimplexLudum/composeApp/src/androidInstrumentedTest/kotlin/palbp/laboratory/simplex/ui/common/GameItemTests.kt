package palbp.laboratory.simplex.ui.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.assertTrue
import kotlin.test.Test
import palbp.laboratory.simplex.domain.Distribution
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.Genre
import palbp.laboratory.simplex.domain.Platform

@OptIn(ExperimentalTestApi::class)
class GameItemTests {

    @Test
    fun pressing_a_game_item_emits_open_details() = runComposeUiTest {
        // Arrange
        val game = Game(
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )
        var onOpenDetailsIntentCalled = false
        setContent {
            GameItem(
                game = game,
                onGameItemSelected = { onOpenDetailsIntentCalled = true }
            )
        }

        // Act
        onNodeWithTag(computeGameItemTag(game), true)
            .performClick()

        // Assert
        assertTrue(onOpenDetailsIntentCalled)
    }
}
