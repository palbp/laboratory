package palbp.laboratory.demos.quoteofday.quotes.daily

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import palbp.laboratory.demos.quoteofday.DependenciesContainer
import palbp.laboratory.demos.quoteofday.R
import palbp.laboratory.demos.quoteofday.TAG
import palbp.laboratory.demos.quoteofday.info.InfoActivity
import palbp.laboratory.demos.quoteofday.quotes.ApiException
import palbp.laboratory.demos.quoteofday.quotes.LocalQuoteDto
import palbp.laboratory.demos.quoteofday.quotes.Quote
import palbp.laboratory.demos.quoteofday.quotes.weekly.QuotesListActivity
import palbp.laboratory.demos.quoteofday.ui.ErrorAlert
import palbp.laboratory.demos.quoteofday.ui.NavigationHandlers
import palbp.laboratory.demos.quoteofday.ui.RefreshingState
import palbp.laboratory.demos.quoteofday.utils.viewModelInit
import java.io.IOException

/**
 * The activity that hosts the screen for displaying a single quote. The quote
 * to be displayed may be either fetched from the API, in which case it will be
 * the day's quote, or received as an Intent extra.
 */
class QuoteActivity : ComponentActivity() {

    companion object {
        const val QUOTE_EXTRA = "QUOTE_EXTRA"
        fun navigate(origin: Activity, quote: LocalQuoteDto? = null) {
            with(origin) {
                val intent = Intent(this, QuoteActivity::class.java)
                if (quote != null)
                    intent.putExtra(QUOTE_EXTRA, quote)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: QuoteScreenViewModel by viewModels {
        viewModelInit {
            QuoteScreenViewModel(dependencies.quoteService)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "QuoteActivity.onCreate()")
        val receivedExtra = quoteExtra
        if (receivedExtra != null) {
            setContent {
                QuoteScreen(
                    state = QuoteScreenState(Quote(receivedExtra), RefreshingState.Idle),
                    onNavigationRequested = NavigationHandlers(
                        onInfoRequested = { InfoActivity.navigate(origin = this) },
                        onBackRequested = { finish() }
                    )
                )
            }
        }
        else {
            if (viewModel.quote == null)
                viewModel.fetchQuote()

            setContent {
                val loadingState: RefreshingState =
                    if (viewModel.isLoading) RefreshingState.Refreshing
                    else RefreshingState.Idle

                QuoteScreen(
                    state = QuoteScreenState(viewModel.quote?.getOrNull(), loadingState),
                    onUpdateRequest = { viewModel.fetchQuote(forcedRefresh = true) },
                    onNavigationRequested = NavigationHandlers(
                        onInfoRequested = { InfoActivity.navigate(origin = this) },
                        onHistoryRequested = { QuotesListActivity.navigate(origin = this) }
                    )
                )

                if (viewModel.quote?.isFailure == true)
                    ErrorMessage()
            }
        }
    }

    @Composable
    private fun ErrorMessage() {
        try { viewModel.quote?.getOrThrow() }
        catch (e: IOException) {
            ErrorAlert(
                title = R.string.error_api_title,
                message = R.string.error_could_not_reach_api,
                buttonText = R.string.error_retry_button_text,
                onDismiss = { viewModel.fetchQuote() }
            )
        }
        catch (e: ApiException) {
            ErrorAlert(
                title = R.string.error_api_title,
                message = R.string.error_unknown_api_response,
                buttonText = R.string.error_exit_button_text,
                onDismiss = { finishAndRemoveTask() }
            )
        }
    }

    @Suppress("deprecation")
    private val quoteExtra: LocalQuoteDto?
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                intent.getParcelableExtra(QUOTE_EXTRA, LocalQuoteDto::class.java)
            else
                intent.getParcelableExtra(QUOTE_EXTRA)
}

