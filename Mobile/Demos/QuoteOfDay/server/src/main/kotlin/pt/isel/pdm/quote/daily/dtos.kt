package pt.isel.pdm.quote.daily

import pt.isel.pdm.quote.hypermedia.SirenEntity

fun Quote.toSirenEntity() = SirenEntity(
    clazz = listOf("Quote"),
    properties = this
)
