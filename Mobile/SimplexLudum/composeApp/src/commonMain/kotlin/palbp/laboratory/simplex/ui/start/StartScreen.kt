package palbp.laboratory.simplex.ui.start

import androidx.compose.runtime.Composable

/**
 * The start screen route.
 */
const val startRoute = "start"

/**
 * The start screen root composable.
 * @param onGetStartedIntent Called when the user intends to start using the application's
 * functionality.
 */
@Composable
fun StartScreen(onGetStartedIntent: () -> Unit) {
    StartScreenView(onGetStartedIntent)
}
