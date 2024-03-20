package palbp.laboratory.simplexludum.ui.common

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform

class GameItemTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_a_game_item_emits_open_details() {
        // Arrange
        val game = Game(
            name = "name1",
            developer = "developer1",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )
        var onOpenDetailsIntentCalled = false
        composeTree.setContent {
            GameItem(
                game = game,
                onOpenGameDetailsIntent = { onOpenDetailsIntentCalled = true }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameItemTag(game), true)
            .performClick()

        // Assert
        Assert.assertTrue(onOpenDetailsIntentCalled)
    }
}
