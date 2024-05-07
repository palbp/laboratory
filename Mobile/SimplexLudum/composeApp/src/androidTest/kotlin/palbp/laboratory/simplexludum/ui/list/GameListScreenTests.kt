package palbp.laboratory.simplexludum.ui.list

import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import kotlin.test.fail

class GameListScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun screen_data_is_fetched_once_the_screen_enters_the_composition() {
        // Arrange
        val screenModel = mockk<GameListScreenModel>(relaxed = true) {
            every { state } returns GameListScreenState.Idle
        }
        val sut = GameListScreen(listTitle = "", screenModel = screenModel)

        // Act
        composeTree.setContent { sut.Content() }
        composeTree.waitForIdle()

        // Assert
        verify { screenModel.fetchFilteredGameList(query = "") }
    }

    @Test
    fun pressing_navigate_back_destroys_the_screen() {
        fail("Not yet implemented")
    }

    @Test
    fun pressing_a_game_item_navigates_to_its_details_screen() {
        fail("To be implemented")
    }
}