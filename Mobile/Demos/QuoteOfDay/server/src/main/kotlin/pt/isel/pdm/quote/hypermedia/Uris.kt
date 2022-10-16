package pt.isel.pdm.quote.hypermedia

import java.net.URI

object Uris {
    const val HOME = "/"
    const val DAILY_QUOTE = "/today"
    const val WEEK_QUOTES = "/week"

    const val QUOTE_BY_ID = "/quotes/{qid}"

    fun home(baseUrl: String) = URI("$baseUrl$HOME")
    fun dailyQuote(baseUrl: String) = URI("$baseUrl$DAILY_QUOTE")
    fun weekQuotes(baseUrl: String) = URI("$baseUrl$WEEK_QUOTES")
    fun quoteById(baseUrl: String, qid: Long) = URI("$baseUrl/quotes/$qid")
}
