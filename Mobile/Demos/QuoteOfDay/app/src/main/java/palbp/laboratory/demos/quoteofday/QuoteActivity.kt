package palbp.laboratory.demos.quoteofday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class QuoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var loadingState by remember { mutableStateOf(LoadingState.Idle) }
            QuoteOfDayScreen(
                loadingState = loadingState,
                onUpdateRequest = { loadingState = LoadingState.Loading }
            )
        }
    }
}
