package palbp.laboratory.demos.quoteofday

interface QuoteService {
    suspend fun fetchQuote(): Quote
}
