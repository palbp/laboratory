package palbp.laboratory.simplexludum.ui.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.common.SEARCH_INPUT_TAG
import palbp.laboratory.simplexludum.ui.common.computeGameItemTag
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GameListViewTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_back_navigation_button_emits_navigate_back_intent() {
        // Arrange
        var navigateBackCalled = false
        composeTree.setContent {
            GameListView(
                listTitle = "Some Game List",
                games = emptyList(),
                onSearchIntent = { },
                onOpenGameDetailsIntent = { },
                onNavigateBackIntent = { navigateBackCalled = true }
            )
        }

        // Act
        composeTree.onNodeWithTag(BACK_BUTTON_TAG).performClick()

        // Assert
        assertTrue(navigateBackCalled)
    }

    @Test
    fun sending_input_to_searchBox_emits_search_intent_with_that_input() {
        // Arrange
        var searchQuery: String? = null
        val query = "search text"
        composeTree.setContent {
            GameListView(
                listTitle = "Some Game List",
                games = emptyList(),
                onSearchIntent = { searchQuery = it },
                onOpenGameDetailsIntent = { },
                onNavigateBackIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = query)

        // Assert
        assertNotNull(searchQuery)
        assertEquals(query, searchQuery)
    }

    @Test
    fun clicking_a_game_item_emits_navigate_to_game_intent() {
        // Arrange
        var gameToNavigateTo: Game? = null
        val game = Game(
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )
        composeTree.setContent {
            GameListView(
                listTitle = "Some Game List",
                games = listOf(game),
                onSearchIntent = { },
                onNavigateBackIntent = { },
                onOpenGameDetailsIntent = { gameToNavigateTo = it }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameItemTag(game))
            .performClick()

        // Assert
        assertEquals(game, gameToNavigateTo)
    }
}
