package palbp.laboratory.demos.quoteofday

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import kotlinx.coroutines.delay
import palbp.laboratory.demos.quoteofday.main.Quote
import palbp.laboratory.demos.quoteofday.main.QuoteService

const val FAKE_FETCH_DELAY = 2000L

private class TestFakeQuoteService : QuoteService {
    override suspend fun fetchQuote(): Quote {
        delay(FAKE_FETCH_DELAY)
        return Quote("Test text", "Test author")
    }
}

class QuoteOfDayTestApplication : DependenciesContainer, Application() {
    override val quoteService: QuoteService by lazy { TestFakeQuoteService() }
}

@Suppress("unused")
class QuoteOfDayTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, QuoteOfDayTestApplication::class.java.name, context)
    }
}