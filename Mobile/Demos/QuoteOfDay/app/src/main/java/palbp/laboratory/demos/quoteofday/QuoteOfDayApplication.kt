package palbp.laboratory.demos.quoteofday

import android.app.Application
import kotlinx.coroutines.delay
import palbp.laboratory.demos.quoteofday.main.Quote
import palbp.laboratory.demos.quoteofday.main.QuoteService

const val TAG = "QuoteOfDayDemo"

interface DependenciesContainer {
    val quoteService: QuoteService
}

class QuoteOfDayApplication : DependenciesContainer, Application() {
    override val quoteService: QuoteService by lazy { FakeQuoteService() }
}

private class FakeQuoteService : QuoteService {
    override suspend fun fetchQuote(): Quote {
        delay(3000)
        val quoteText = "O poeta é um fingidor.\n" +
                "Finge tão completamente\n" +
                "Que chega a fingir que é dor\n" +
                "A dor que deveras sente."

        return Quote(quoteText, "Fernando Pessoa")
    }
}
