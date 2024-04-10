package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.simplexludum.ui.mycollection.explore.EXPLORE_LIBRARY_TAG
import palbp.laboratory.simplexludum.ui.mycollection.find.FIND_GAMES_TAG
import palbp.laboratory.simplexludum.ui.mycollection.home.HOME_TAG

class MyCollectionScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun initially_the_displayed_tab_is_the_home_tab() {
        // Arrange
        composeTree.setContent {
            MyCollectionScreen().Content()
        }
        // Act

        // Assert
        composeTree
            .onNodeWithTag(HOME_TAG, true)
            .assertIsDisplayed()
    }

    @Test
    fun pressing_the_home_tab_selector_displays_the_home_tab() {
        // Arrange
        composeTree.setContent {
            MyCollectionScreen().Content()
        }

        // Act
        composeTree
            .onNodeWithTag(HOME_TAB_TAG, true)
            .performClick()
        composeTree.waitForIdle()

        // Assert
        composeTree
            .onNodeWithTag(HOME_TAG, true)
            .assertIsDisplayed()
    }

    @Test
    fun pressing_the_find_tab_selector_displays_the_find_tab() {
        // Arrange
        composeTree.setContent {
            MyCollectionScreen().Content()
        }

        // Act
        composeTree
            .onNodeWithTag(FIND_GAMES_TAB_TAG, true)
            .performClick()
        composeTree.waitForIdle()

        // Assert
        composeTree
            .onNodeWithTag(FIND_GAMES_TAG, true)
            .assertIsDisplayed()
    }

    @Test
    fun pressing_the_explore_tab_selector_displays_the_explore_tab() {
        // Arrange
        composeTree.setContent {
            MyCollectionScreen().Content()
        }

        // Act
        composeTree
            .onNodeWithTag(EXPLORE_LIBRARY_TAB_TAG, true)
            .performClick()
        composeTree.waitForIdle()

        // Assert
        composeTree
            .onNodeWithTag(EXPLORE_LIBRARY_TAG, true)
            .assertIsDisplayed()
    }
}