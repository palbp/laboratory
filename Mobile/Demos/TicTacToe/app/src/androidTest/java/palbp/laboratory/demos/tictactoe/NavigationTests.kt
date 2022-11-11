package palbp.laboratory.demos.tictactoe

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.tictactoe.main.MainActivity

@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @get:Rule
    val testRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun preferences_screen_is_not_included_in_the_backstack() {

    }
}