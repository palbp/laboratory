package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import cafe.adriel.voyager.navigator.tab.TabNavigator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.list.LIST_TITLE_TAG
import kotlin.test.fail

class HomeTabTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun screen_data_is_fetched_once_the_screen_enters_the_composition() {
        // Arrange
        val screenModel = mockk<HomeTabScreenModel>(relaxed = true) {
            every { state } returns HomeTabScreenState.Idle
        }
        val sut = HomeTab(tabIndex = 0u, tabModel = screenModel)

        // Act
        composeTree.setContent { sut.Content() }
        composeTree.waitForIdle()

        // Assert
        verify { screenModel.fetchScreenData() }
    }

    @Test
    fun pressing_a_game_list_summary_navigates_to_its_game_list_screen() {
        // Arrange
        val screenModel = mockk<HomeTabScreenModel>(relaxed = true) {
            every { state } returns HomeTabScreenState.Loaded(lists = testGameLists, latest = testLatestGames)
        }
        val sut = HomeTab(tabIndex = 0u, tabModel = screenModel)
        composeTree.setContent { sut.Content() }

        // Act
        val listTitle = testGameLists.first().name.toString()
        composeTree.onNodeWithText(listTitle).performClick()
        composeTree.waitForIdle()

        // Assert
        composeTree.onNodeWithTag(LIST_TITLE_TAG).assertIsDisplayed()
        composeTree.onNodeWithTag(LIST_TITLE_TAG).assertTextEquals(listTitle)
    }

    @Test
    fun pressing_a_game_item_navigates_to_its_details_screen() {
        fail("To be implemented")
    }
}

private val testGameLists = listOf(
    GameListSummary("Platinum", 19),
    GameListSummary("Completed", 36),
)

private val testLatestGames = listOf(
    Game(
        name = "Elden Ring",
        developer = "FromSoftware",
        genres = setOf(Genre.ADVENTURE, Genre.RPG),
        platform = Platform.PS5,
        distribution = Distribution.PHYSICAL
    ),
    Game(
        name = "Demon's Souls",
        developer = "FromSoftware",
        genres = setOf(Genre.ADVENTURE, Genre.RPG),
        platform = Platform.PS5,
        distribution = Distribution.PHYSICAL
    ),
)