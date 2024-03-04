package palbp.laboratory.simplexludum

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import palbp.laboratory.simplexludum.ui.start.StartScreen

@Composable
fun App() {
    Navigator(StartScreen)
}
