package palbp.laboratory.simplexludum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun App() {
    var currentScreen: String by remember { mutableStateOf(HOME_URI) }
    when (currentScreen) {
        HOME_URI -> StartScreen(onGetStartedIntent = { currentScreen = MY_COLLECTION_URI })
        MY_COLLECTION_URI -> MyCollectionScreen()
    }
}
