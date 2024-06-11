package palbp.laboratory.simplex.ui.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class SearchBoxTests {

    @Test
    fun clicking_cancel_icon_clears_searchBox_and_retains_focus() = runComposeUiTest {
        // Arrange
        setContent {
            SearchBox(onSearchRequested = { })
        }
        onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = "search text")

        // Act
        onNodeWithTag(CANCEL_ICON_TAG)
            .performClick()

        // Assert
        onNodeWithTag(SEARCH_INPUT_TAG)
            .assert(hasText(""))
            .assertIsFocused()
    }

    @Test
    fun clicking_cancel_button_clears_searchBox_and_removes_focus() = runComposeUiTest {
        // Arrange
        setContent {
            SearchBox(onSearchRequested = { })
        }
        onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = "search text")

        // Act
        onNodeWithTag(CANCEL_BUTTON_TAG)
            .performClick()

        // Assert
        onNodeWithTag(SEARCH_INPUT_TAG)
            .assert(hasText(""))
            .assertIsNotFocused()
    }

    @Test
    fun entering_text_in_searchBox_calls_onSearchRequested() = runComposeUiTest {
        // Arrange
        val query = "search text"
        var searchQuery = ""
        setContent {
            SearchBox(onSearchRequested = { searchQuery = it })
        }

        // Act
        onNodeWithTag(SEARCH_INPUT_TAG)
            .performTextInput(text = query)

        // Assert
        assertEquals(expected = query, actual = searchQuery)
    }
}