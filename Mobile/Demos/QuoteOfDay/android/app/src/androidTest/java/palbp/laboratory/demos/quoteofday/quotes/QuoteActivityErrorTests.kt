package palbp.laboratory.demos.quoteofday.quotes

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.quoteofday.quotes.daily.QuoteActivity

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class QuoteActivityErrorTests {

    @get:Rule
    val testRule = createEmptyComposeRule()

    @Test
    fun error_message_is_displayed_when_fetchQuote_produces_error() {
        ActivityScenario.launch(QuoteActivity::class.java).use {
            testRule.onNodeWithTag("QuoteView").assertExists()
            TODO()
        }
    }
}