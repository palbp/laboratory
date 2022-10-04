package palbp.laboratory.demos.quoteofday

import android.app.Application
import kotlinx.coroutines.delay
import palbp.laboratory.demos.quoteofday.daily.Quote
import palbp.laboratory.demos.quoteofday.daily.QuoteService
import palbp.laboratory.demos.quoteofday.daily.RealQuoteService
import java.net.URL

const val TAG = "QuoteOfDayDemo"

interface DependenciesContainer {
    val quoteService: QuoteService
}

private val quoteAPIHome = URL("https://184b-2001-690-2008-df53-d4d7-fc3e-10c4-b7b2.ngrok.io")

class QuoteOfDayApplication : DependenciesContainer, Application() {
    override val quoteService: QuoteService by lazy { RealQuoteService(quoteAPIHome) }
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
