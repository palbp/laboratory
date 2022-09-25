package palbp.laboratory.demos.quoteofday.daily

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import palbp.laboratory.demos.quoteofday.DependenciesContainer
import palbp.laboratory.demos.quoteofday.TAG
import palbp.laboratory.demos.quoteofday.daily.views.LoadingState
import palbp.laboratory.demos.quoteofday.info.InfoActivity

class QuoteActivity : ComponentActivity() {

    private val quoteService: QuoteService by lazy {
        (application as DependenciesContainer).quoteService
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel: QuoteScreenViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuoteScreenViewModel(quoteService) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "application: ${application.javaClass.name}")
        Log.v(TAG, "applicationContext: ${applicationContext.javaClass.name}")
        setContent {
            val loadingState: LoadingState =
                if (viewModel.isLoading) LoadingState.Loading
                else LoadingState.Idle

            QuoteOfDayScreen(
                quote = viewModel.quote,
                loadingState = loadingState,
                onUpdateRequest = {
                    Log.v(TAG, "QuoteActivity.onUpdateRequest()")
                    viewModel.fetchQuote()
                },
                onInfoRequest = { navigateToInfoScreen() }
            )
        }
    }

    private fun navigateToInfoScreen() {
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }
}

