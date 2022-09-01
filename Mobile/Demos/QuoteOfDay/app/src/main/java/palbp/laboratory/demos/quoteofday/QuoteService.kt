package palbp.laboratory.demos.quoteofday

interface QuoteService {
    fun fetchQuote(): Quote
}

class FakeQuoteService : QuoteService {
    override fun fetchQuote(): Quote {
        val quoteText = "O poeta é um fingidor.\n" +
                "Finge tão completamente\n" +
                "Que chega a fingir que é dor\n" +
                "A dor que deveras sente."

        return Quote(quoteText, "Fernando Pessoa")
    }
}