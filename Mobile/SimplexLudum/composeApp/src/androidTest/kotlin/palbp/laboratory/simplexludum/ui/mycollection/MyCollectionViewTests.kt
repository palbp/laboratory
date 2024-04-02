package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.common.computeGameItemTag
import palbp.laboratory.simplexludum.ui.common.computeGameListSelectorNavIconTag
import palbp.laboratory.simplexludum.ui.common.computeGameListSelectorTag

class MyCollectionViewTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_a_game_item_emits_open_details() {
        // Arrange
        var onOpenDetailsIntentCalled = false
        composeTree.setContent {
            MyCollectionView(
                lists = emptyList(),
                latest = latest,
                onOpenGameDetailsIntent = { onOpenDetailsIntentCalled = true },
                onOpenGameListIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameItemTag(latest.first()), true)
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
            MyCollectionView(
                lists = listOf(gameList),
                latest = latest,
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameListSelectorTag(gameList), true)
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
            MyCollectionView(
                lists = listOf(gameList),
                latest = latest,
                onOpenGameListIntent = {
                    onOpenListIntentCalled = true
                    onOpenIntentParam = it
                },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameListSelectorNavIconTag(gameList), true)
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
            MyCollectionView(
                lists = gameLists,
                latest = latest,
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        composeTree
            .onNodeWithTag(computeGameListSelectorTag(gameList), true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
    }
}

// Test data

private val gameLists = listOf(
    GameListSummary("name1", 1),
    GameListSummary("name2", 2)
)

private val latest = listOf(
    Game(
        name = "name1",
        developer = "developer1",
        genres = setOf(Genre.ADVENTURE),
        platform = Platform.PS4,
        distribution = Distribution.PHYSICAL
    ),
    Game(
        name = "name2",
        developer = "developer1",
        genres = setOf(Genre.ACTION),
        platform = Platform.PS5,
        distribution = Distribution.SUBSCRIPTION
    ),
)
