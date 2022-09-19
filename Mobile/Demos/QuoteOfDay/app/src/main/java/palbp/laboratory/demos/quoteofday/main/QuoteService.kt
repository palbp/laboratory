package palbp.laboratory.demos.quoteofday.main

interface QuoteService {
    suspend fun fetchQuote(): Quote
}
