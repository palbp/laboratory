package palbp.laboratory.demos.quoteofday

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "QuoteOfDayDemo"

class QuoteActivity : ComponentActivity() {

    private val quoteService: QuoteService by lazy {
        (application as DependenciesContainer).quoteService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "application: ${application.javaClass.name}")
        Log.v(TAG, "applicationContext: ${applicationContext.javaClass.name}")
        setContent {
            var loadingState by remember { mutableStateOf(LoadingState.Idle) }
            var quote by remember { mutableStateOf<Quote?>(null) }

            QuoteOfDayScreen(
                quote = quote,
                loadingState = loadingState,
                onUpdateRequest = {
                    Log.v(TAG, "QuoteActivity.onUpdateRequest()")
                    CoroutineScope(Dispatchers.Main).launch {
                        quote = null
                        loadingState = LoadingState.Loading
                        Log.v(TAG, "QuoteActivity.onUpdateRequest().before fetch")
                        quote = quoteService.fetchQuote()
                        Log.v(TAG, "QuoteActivity.onUpdateRequest().after fetch with ${quote?.author}")
                        loadingState = LoadingState.Idle
                    }
                }
            )
        }
    }
}
