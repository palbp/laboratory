package palbp.laboratory.demos.tictactoe.game.play.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Ignore("not implemented yet")
class GameActivityTests {

    @get:Rule
    val testRule = createAndroidComposeRule<GameActivity>()

    @Test
    fun game_screen_is_displayed() {
        testRule.onNodeWithTag(GameScreenTag).assertExists()
    }

    @Test
    fun game_activity_starts_by_displaying_starting_dialog() {
        // Assert
        testRule.onNodeWithTag(StartingMatchDialogTag).assertExists()
    }
}
