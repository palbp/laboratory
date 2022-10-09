package pt.isel.pdm.quote.daily

/**
 * Represents the quote domain entity.
 * @property id     - The quote identifier
 * @property author - The author
 * @property text   - The quote's text
 */
data class Quote(val id: Long, val author: String, val text: String) {
    init {
        if (author.isBlank() || text.isBlank())
            throw IllegalArgumentException("Quotes cannot include blank strings")
    }
}