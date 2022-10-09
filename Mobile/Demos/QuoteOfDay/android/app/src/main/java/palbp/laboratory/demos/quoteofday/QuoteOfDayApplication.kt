package palbp.laboratory.demos.quoteofday

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import palbp.laboratory.demos.quoteofday.daily.Quote
import palbp.laboratory.demos.quoteofday.daily.QuoteService
import palbp.laboratory.demos.quoteofday.daily.RealQuoteService
import java.net.URL

const val TAG = "QuoteOfDayDemo"

interface DependenciesContainer {
    val quoteService: QuoteService
    val jsonEncoder: Gson
}

private val quoteAPIHome = URL("https://212a-194-210-190-193.ngrok.io")

class QuoteOfDayApplication : DependenciesContainer, Application() {

    override val jsonEncoder: Gson
        get() = GsonBuilder().create()

    override val quoteService: QuoteService by lazy {
        RealQuoteService(quoteAPIHome, jsonEncoder)
    }
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
