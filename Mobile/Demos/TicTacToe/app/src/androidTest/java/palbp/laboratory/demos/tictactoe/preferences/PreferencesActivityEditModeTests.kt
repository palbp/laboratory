package palbp.laboratory.demos.tictactoe.preferences

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.tictactoe.testutils.PreserveDefaultDependencies
import palbp.laboratory.demos.tictactoe.testutils.createPreserveDefaultDependenciesComposeRule

@RunWith(AndroidJUnit4::class)
class PreferencesActivityEditModeTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRule()

    private val application by lazy {
        (testRule.activityRule as PreserveDefaultDependencies).testApplication
    }

    @Test
    fun screen_has_update_button_if_user_info_does_not_exist() {

        application.userInfoRepo = mockk {
            every { userInfo } returns null
        }

        ActivityScenario.launch(PreferencesActivity::class.java).use {
            testRule.onNodeWithTag(UpdateButtonTag).assertExists()
        }
    }

    @Test
    fun screen_update_button_is_disabled_if_entered_info_is_not_valid() {
        application.userInfoRepo = mockk {
            every { userInfo } returns null
        }

        ActivityScenario.launch(PreferencesActivity::class.java).use {
            testRule.onNodeWithTag(UpdateButtonTag).assertIsNotEnabled()
        }
    }

    @Test
    fun screen_update_button_becomes_enabled_if_entered_info_is_valid() {
        application.userInfoRepo = mockk {
            every { userInfo } returns null
        }

        ActivityScenario.launch(PreferencesActivity::class.java).use {
            testRule.onNodeWithTag(UpdateButtonTag).assertIsNotEnabled()

            testRule.onNodeWithTag(NicknameInputTag).performTextInput("nick")
            testRule.waitForIdle()

            testRule.onNodeWithTag(UpdateButtonTag).assertIsEnabled()
        }
    }
}