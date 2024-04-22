package palbp.laboratory.simplexludum.ui.common

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SearchBoxTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun clicking_cancel_icon_clears_searchBox_and_retains_focus() {
        // Arrange
        composeTree.setContent {
            SearchBox(onSearchRequested = { })
        }
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = "search text")

        // Act
        composeTree
            .onNodeWithTag(CANCEL_ICON_TAG)
            .performClick()

        // Assert
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .assert(hasText(""))
            .assertIsFocused()
    }

    @Test
    fun clicking_cancel_button_clears_searchBox_and_removes_focus() {
        // Arrange
        composeTree.setContent {
            SearchBox(onSearchRequested = { })
        }
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = "search text")

        // Act
        composeTree
            .onNodeWithTag(CANCEL_BUTTON_TAG)
            .performClick()

        // Assert
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .assert(hasText(""))
            .assertIsNotFocused()
    }

    @Test
    fun entering_text_in_searchBox_calls_onSearchRequested() {
        // Arrange
        val query = "search text"
        var searchQuery = ""
        composeTree.setContent {
            SearchBox(onSearchRequested = { searchQuery = it })
        }

        // Act
        composeTree
            .onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = query)

        // Assert
        assertEquals(expected = query, actual = searchQuery)
    }
}