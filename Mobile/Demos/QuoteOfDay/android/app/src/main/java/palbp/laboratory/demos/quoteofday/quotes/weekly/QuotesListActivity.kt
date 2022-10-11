package palbp.laboratory.demos.quoteofday.quotes.weekly

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import palbp.laboratory.demos.quoteofday.DependenciesContainer
import palbp.laboratory.demos.quoteofday.info.InfoActivity
import palbp.laboratory.demos.quoteofday.utils.viewModelFactory

class QuotesListActivity : ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, QuotesListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: QuotesListScreenViewModel by viewModels {
        viewModelFactory(vmInit = {
            val service = dependencies.quoteService
            QuotesListScreenViewModel(service)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesListScreen(
                state = QuotesListScreenState(viewModel.quotes, viewModel.isLoading),
                onBackRequested = { finish() },
                onInfoRequest = { InfoActivity.navigate(origin = this) },
                onUpdateRequest = { viewModel.fetchWeekQuotes() }
            )
        }
    }
}