package palbp.laboratory.simplex.ui.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.Test
import palbp.laboratory.simplex.domain.Distribution
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.Genre
import palbp.laboratory.simplex.domain.Platform
import palbp.laboratory.simplex.ui.common.SEARCH_INPUT_TAG
import palbp.laboratory.simplex.ui.common.computeGameItemTag

@OptIn(ExperimentalTestApi::class)
class GameListViewTests {

    @Test
    fun pressing_back_navigation_button_emits_navigate_back_intent() = runComposeUiTest {
        // Arrange
        var navigateBackCalled = false
        setContent {
            GameListView(
                listTitle = "Some Game List",
                games = emptyList(),
                onSearchIntent = { },
                onOpenGameDetailsIntent = { },
                onNavigateBackIntent = { navigateBackCalled = true }
            )
        }

        // Act
        onNodeWithTag(BACK_BUTTON_TAG)
            .performClick()

        // Assert
        assertTrue(navigateBackCalled)
    }

    @Test
    fun sending_input_to_searchBox_emits_search_intent_with_that_input() = runComposeUiTest {
        // Arrange
        var searchQuery: String? = null
        val query = "search text"
        setContent {
            GameListView(
                listTitle = "Some Game List",
                games = emptyList(),
                onSearchIntent = { searchQuery = it },
                onOpenGameDetailsIntent = { },
                onNavigateBackIntent = { }
            )
        }

        // Act
        onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = query)

        // Assert
        assertNotNull(searchQuery)
        assertEquals(query, searchQuery)
    }

    @Test
    fun clicking_a_game_item_emits_navigate_to_game_intent() = runComposeUiTest {
        // Arrange
        var gameToNavigateTo: Game? = null
        val game = Game(
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )
        setContent {
            GameListView(
                listTitle = "Some Game List",
                games = listOf(game),
                onSearchIntent = { },
                onNavigateBackIntent = { },
                onOpenGameDetailsIntent = { gameToNavigateTo = it }
            )
        }

        // Act
        onNodeWithTag(computeGameItemTag(game))
            .performClick()

        // Assert
        assertEquals(game, gameToNavigateTo)
    }
}
