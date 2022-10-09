package pt.isel.pdm.quote.daily

/**
 * Service that provides the quotes for the day.
 */
interface QuotesService {

    /**
     * Gets the quote for the day.
     * @return Today's quote
     */
    fun getQuoteForToday(): Quote
}