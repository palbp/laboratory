package palbp.laboratory.simplexludum

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class StartScreenTests {

    @get:Rule
    val composeTree = createComposeRule()

    @Test
    fun pressing_start_button_emits_get_started() {
        // Arrange
        var onStartIntentCalled = false
        composeTree.setContent {
            StartScreen(onGetStartedIntent = { onStartIntentCalled = true })
        }

        // Act
        composeTree
            .onNodeWithTag(GET_STARTED_BUTTON_TAG, true)
            .performClick()

        // Assert
        assertTrue("onGetStartedIntent not called",  onStartIntentCalled)
    }
}