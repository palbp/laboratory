package palbp.laboratory.simplexludum.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.GameListSummary

private val gameLists = listOf(
    GameListSummary("name1", 1),
    GameListSummary("name2", 2)
)

class MyCollectionScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_a_game_item_emits_open_details() {
        // Arrange
        var onOpenDetailsIntentCalled = false
        composeTree.setContent {
            MyCollectionScreen(
                lists = emptyList(),
                onOpenGameDetailsIntent = { onOpenDetailsIntentCalled = true },
                onOpenGameListIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag("$GAME_ITEM_BASE_TAG-0", true)
            .performClick()

        // Assert
        assertTrue("onOpenDetailsIntent not called",  onOpenDetailsIntentCalled)
    }

    @Test
    fun pressing_the_last_game_list_selector_emits_open_list() {
        // Arrange
        var onOpenListIntentCalled = false
        val gameList = gameLists.last()
        composeTree.setContent {
            MyCollectionScreen(
                lists = listOf(gameList),
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag("$GAME_LIST_SELECTOR_TAG-${gameList.name}", true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
    }

    @Test
    fun pressing_a_game_list_selector_navigation_icon_emits_open_list() {
        // Arrange
        var onOpenListIntentCalled = false
        var onOpenIntentParam: GameListSummary? = null
        val gameList = GameListSummary("name", 0)
        composeTree.setContent {
            MyCollectionScreen(
                lists = listOf(gameList),
                onOpenGameListIntent = {
                    onOpenListIntentCalled = true
                    onOpenIntentParam = it
                },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag("$GAME_LIST_SELECTOR_NAV_ICON_TAG-${gameList.name}", true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
        assertTrue("onOpenListIntent param not the expected",  onOpenIntentParam == gameList)
    }

    @Test
    fun pressing_the_first_game_list_selector_emits_open_list() {
        // Arrange
        var onOpenListIntentCalled = false
        val gameList = gameLists.first()
        composeTree.setContent {
            MyCollectionScreen(
                lists = gameLists,
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag("$GAME_LIST_SELECTOR_TAG-${gameList.name}", true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
    }
}