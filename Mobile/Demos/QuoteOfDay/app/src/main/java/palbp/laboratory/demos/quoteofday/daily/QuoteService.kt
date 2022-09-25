package palbp.laboratory.demos.quoteofday.daily

interface QuoteService {
    suspend fun fetchQuote(): Quote
}
