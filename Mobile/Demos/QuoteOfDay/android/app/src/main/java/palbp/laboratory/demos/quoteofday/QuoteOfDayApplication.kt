package palbp.laboratory.demos.quoteofday

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import palbp.laboratory.demos.quoteofday.quotes.Quote
import palbp.laboratory.demos.quoteofday.quotes.QuoteService
import palbp.laboratory.demos.quoteofday.quotes.RealQuoteService
import java.net.URL

const val TAG = "QuoteOfDayDemo"

interface DependenciesContainer {
    val quoteService: QuoteService
}

private val quoteAPIHome = URL("https://992d-2001-690-2008-df53-5022-1527-5050-d281.ngrok.io")

class QuoteOfDayApplication : DependenciesContainer, Application() {

    private val httpClient: OkHttpClient by lazy { OkHttpClient() }
    private val jsonEncoder: Gson by lazy { GsonBuilder().create() }

    override val quoteService: QuoteService by lazy {
//        FakeQuoteService()
        RealQuoteService(
            httpClient = httpClient,
            jsonEncoder = jsonEncoder,
            quoteHome = quoteAPIHome
        )
    }
}

@Suppress("unused")
private class FakeQuoteService : QuoteService {
    private val aQuote = Quote(
        text = "O poeta é um fingidor.\n" +
                "Finge tão completamente\n" +
                "Que chega a fingir que é dor\n" +
                "A dor que deveras sente.",
        author = "Fernando Pessoa"
    )

    override suspend fun fetchQuote(): Quote {
        delay(3000)
        return aQuote
    }

    override suspend fun fetchWeekQuotes(): List<Quote> {
        delay(3000)
        return buildList { repeat(20) { add(aQuote) } }
    }
}

