package palbp.laboratory.simplexludum.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform

private val game = Game(
    name = "name1",
    developer = "developer1",
    genres = listOf(Genre.ADVENTURE),
    platform = Platform.PS4,
    distribution = Distribution.PHYSICAL
)


class GameItemTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_a_game_item_emits_open_details() {
        // Arrange
        var onOpenDetailsIntentCalled = false
        composeTree.setContent {
            GameItem(
                game = game,
                onOpenGameDetailsIntent = { onOpenDetailsIntentCalled = true }
            )
        }

        // Act
        composeTree
            .onNodeWithTag("$GAME_ITEM_BASE_TAG-${game.name}", true)
            .performClick()

        // Assert
        assertTrue(onOpenDetailsIntentCalled)
    }
}