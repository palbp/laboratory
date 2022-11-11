package palbp.laboratory.demos.tictactoe.preferences

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import palbp.laboratory.demos.tictactoe.DependenciesContainer
import palbp.laboratory.demos.tictactoe.TAG

/**
 * The screen used to display and edit the user information to be used to identify
 * the player in the lobby.
 */
class PreferencesActivity : ComponentActivity() {

    private val repo by lazy {
        (application as DependenciesContainer).userInfoRepo
    }

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "PreferencesActivity.onCreate()")
        setContent {
            PreferencesScreen(
                userInfo = repo.userInfo,
                onBackRequested = { finish() },
                onSaveRequested = { repo.userInfo = it; finish() }
            )
        }
    }
}