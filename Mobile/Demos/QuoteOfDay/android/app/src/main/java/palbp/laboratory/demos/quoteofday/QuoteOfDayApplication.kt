package palbp.laboratory.demos.quoteofday

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import palbp.laboratory.demos.quoteofday.quotes.Quote
import palbp.laboratory.demos.quoteofday.quotes.QuoteService
import java.net.URL

const val TAG = "QuoteOfDayDemo"

interface DependenciesContainer {
    val quoteService: QuoteService
}

private val quoteAPIHome = URL("https://bb34-2001-818-e22f-ee00-b977-a076-2120-8ac.ngrok.io")

class QuoteOfDayApplication : DependenciesContainer, Application() {

    private val httpClient: OkHttpClient by lazy { OkHttpClient() }
    private val jsonEncoder: Gson by lazy { GsonBuilder().create() }

    override val quoteService: QuoteService by lazy {
        FakeQuoteService()
//        RealQuoteService(
//            httpClient = httpClient,
//            jsonEncoder = jsonEncoder,
//            quoteHome = quoteAPIHome
//        )
    }
}

private class FakeQuoteService : QuoteService {
    override suspend fun fetchQuote(): Quote {
        delay(3000)
        return aQuote
    }

    override suspend fun fetchWeekQuotes(): List<Quote> {
        delay(3000)
        return buildList { repeat(5) { add(aQuote) } }
    }
}

private val aQuote = Quote(
    text = "O poeta é um fingidor.\n" +
            "Finge tão completamente\n" +
            "Que chega a fingir que é dor\n" +
            "A dor que deveras sente.",
    author = "Fernando Pessoa"
)

