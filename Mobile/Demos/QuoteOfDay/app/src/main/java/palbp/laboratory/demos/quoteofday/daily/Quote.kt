package palbp.laboratory.demos.quoteofday.daily

data class Quote(val text: String, val author: String) {
    init {
        require(text.isNotBlank())
        require(author.isNotBlank())
    }
}
