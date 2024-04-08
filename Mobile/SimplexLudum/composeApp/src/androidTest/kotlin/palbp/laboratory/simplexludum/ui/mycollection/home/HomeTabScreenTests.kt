package palbp.laboratory.simplexludum.ui.mycollection.home

import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class HomeTabScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun screen_data_is_fetched_once_the_screen_enters_the_composition() {
        // Arrange
        val screenModel = mockk<HomeTabScreenModel>(relaxed = true) {
            every { state } returns HomeTabScreenState.Idle
        }
        val sut = HomeTabScreen(screenModel)

        // Act
        composeTree.setContent { sut.Content() }
        composeTree.waitForIdle()

        // Assert
        verify { screenModel.fetchScreenData() }
    }
}
