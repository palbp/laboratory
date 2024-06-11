package palbp.laboratory.simplex.ui.mycollection.home

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import palbp.laboratory.simplex.domain.Distribution
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.GameListSummary
import palbp.laboratory.simplex.domain.Genre
import palbp.laboratory.simplex.domain.Platform
import palbp.laboratory.simplex.ui.common.computeGameItemTag
import palbp.laboratory.simplex.ui.common.computeGameListSelectorNavIconTag
import palbp.laboratory.simplex.ui.common.computeGameListSelectorTag
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeTabViewTests {

    @Test
    fun pressing_a_game_item_emits_open_details() = runComposeUiTest {
        // Arrange
        var onOpenDetailsIntentCalled = false
        setContent {
            HomeTabView(
                lists = emptyList(),
                latest = latest,
                onOpenGameDetailsIntent = { onOpenDetailsIntentCalled = true },
                onOpenGameListIntent = { }
            )
        }

        // Act
        onNodeWithTag(computeGameItemTag(latest.first()), true)
            .performClick()

        // Assert
        assertTrue("onOpenDetailsIntent not called",  onOpenDetailsIntentCalled)
    }

    @Test
    fun pressing_the_last_game_list_selector_emits_open_list() = runComposeUiTest {
        // Arrange
        var onOpenListIntentCalled = false
        val gameList = gameLists.last()
        setContent {
            HomeTabView(
                lists = listOf(gameList),
                latest = latest,
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        onNodeWithTag(computeGameListSelectorTag(gameList), true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
    }

    @Test
    fun pressing_a_game_list_selector_navigation_icon_emits_open_list() = runComposeUiTest {
        // Arrange
        var onOpenListIntentCalled = false
        var onOpenIntentParam: GameListSummary? = null
        val gameList = GameListSummary("name", 0)
        setContent {
            HomeTabView(
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
        onNodeWithTag(computeGameListSelectorNavIconTag(gameList), true)
            .performClick()

        // Assert
        assertTrue("onOpenListIntent not called",  onOpenListIntentCalled)
        assertTrue("onOpenListIntent param not the expected",  onOpenIntentParam == gameList)
    }

    @Test
    fun pressing_the_first_game_list_selector_emits_open_list() = runComposeUiTest {
        // Arrange
        var onOpenListIntentCalled = false
        val gameList = gameLists.first()
        setContent {
            HomeTabView(
                lists = gameLists,
                latest = latest,
                onOpenGameListIntent = { onOpenListIntentCalled = true },
                onOpenGameDetailsIntent = { }
            )
        }

        // Act
        onNodeWithTag(computeGameListSelectorTag(gameList), true)
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