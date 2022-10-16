package pt.isel.pdm.quote.daily

import pt.isel.pdm.quote.hypermedia.EmbeddedEntity
import pt.isel.pdm.quote.hypermedia.SirenEntity
import pt.isel.pdm.quote.hypermedia.SirenLink
import pt.isel.pdm.quote.hypermedia.SubEntity
import pt.isel.pdm.quote.hypermedia.Uris
import java.net.URI

fun Quote.toSirenEntity(links: List<SirenLink>) = SirenEntity(
    clazz = listOf("Quote"),
    properties = this,
    links = links
)

fun Quote.toSirenEmbeddedEntity(links: List<SirenLink>) = EmbeddedEntity(
    clazz = listOf("Quote"),
    rel = listOf("quote"),
    properties = this,
    links = links
)

data class QuotesListProperties(val size: Int)

fun List<Quote>.toSirenEntity(baseUrl: String, links: List<SirenLink>) = SirenEntity(
    clazz = listOf("QuotesList"),
    properties = QuotesListProperties(size),
    entities = this.map {
        it.toSirenEmbeddedEntity(links = listOf(
            SirenLink(rel = listOf("self"), href = Uris.quoteById(baseUrl, it.id))
        ))
    },
    links = links
)
