package palbp.laboratory.demos.tictactoe.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import palbp.laboratory.demos.tictactoe.game.LobbyActivity

/**
 * The start screen
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(onStartRequested = { LobbyActivity.navigate(this) })
        }
    }
}
