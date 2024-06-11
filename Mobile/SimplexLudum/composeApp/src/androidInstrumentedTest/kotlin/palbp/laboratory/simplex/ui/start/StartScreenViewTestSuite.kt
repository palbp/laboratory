package palbp.laboratory.simplex.ui.start

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StartScreenViewTestSuite {

    @Test
    fun pressing_start_button_emits_get_started() = runComposeUiTest {
        // Arrange
        var onStartIntentCalled = false
        setContent {
            StartScreenView(onGetStartedIntent = { onStartIntentCalled = true })
        }

        // Act
        onNodeWithTag(GET_STARTED_BUTTON_TAG, true)
            .performClick()

        // Assert
        assertTrue("onGetStartedIntent not called",  onStartIntentCalled)
    }
}
