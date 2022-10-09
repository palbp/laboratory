package pt.isel.pdm.quote.daily

import java.time.LocalDateTime

class FakeQuotesService(private val minutesInADay: Int) : QuotesService {
    init { require(minutesInADay in 1..15) }

    override fun getQuoteForToday(): Quote =
        with(LocalDateTime.now()) {
            fakeQuotesDb[(this.hour * 60 + minute) / minutesInADay % fakeQuotesDb.size]
        }
}

private val fakeQuotesDb: List<Quote> = mutableListOf<Quote>().apply {
    var autoId: Long = 0
    add(
        Quote(
            id = ++autoId,
            author = "Alan Greenspan",
            text = "I know you think you understand what you thought " +
                    "I said but I'm not sure you realize that what you heard is not what I meant."
        )
    )
    add(
        Quote(
            id = ++autoId,
            author = "Epictetus",
            text = "Practice yourself, for heaven's sake, in little things; and thence proceed to greater"
        )
    )
    add(
        Quote(
            id = ++autoId,
            author = "Fernando Pessoa",
            text = "O poeta é um fingidor.\nFinge tão completamente\n" +
                    "que chega a fingir que é dor\na dor que deveras sente."
        )
    )
    add(
        Quote(
            id = ++autoId,
            author = "Plato",
            text = "The price good men pay for indifference to public affairs is to be ruled by evil men."
        )
    )
}
