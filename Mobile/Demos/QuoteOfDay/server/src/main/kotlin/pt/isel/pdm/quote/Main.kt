package pt.isel.pdm.quote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.LoggerFactory
import pt.isel.pdm.quote.daily.FakeQuotesService
import pt.isel.pdm.quote.daily.QuotesService
import pt.isel.pdm.quote.daily.toSirenEntity
import pt.isel.pdm.quote.hypermedia.MediaType
import pt.isel.pdm.quote.hypermedia.SirenLink
import pt.isel.pdm.quote.hypermedia.Uris
import pt.isel.pdm.quote.utils.MediaTypeTypeAdapter
import spark.Request
import spark.Response
import spark.Service.ignite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DEFAULT_PORT = 8080

private val logger = LoggerFactory.getLogger("Quote-Of-Day server")

/**
 * Speeding up time, for demo purposes
 */
private const val MINUTES_IN_A_DAY = 5

fun secondsTilEndOfDay() = with(LocalDateTime.now()) {
    (((this.hour * 60 + minute) % MINUTES_IN_A_DAY + 1) * 60)
}

/**
 * The server's entry point.
 */
fun main(args: Array<String>) {

    println("Quote-Of-Day server is starting ...")
    val port = if (args.size > 1) args[1].toIntOrNull() ?: DEFAULT_PORT else DEFAULT_PORT

    val quotesService = FakeQuotesService(MINUTES_IN_A_DAY)
    val gson = GsonBuilder()
        .registerTypeAdapter(MediaType::class.java, MediaTypeTypeAdapter())
        .create()
    val http = ignite().port(port)

    http.get(Uris.HOME, buildHomeRoute(quotesService, gson))
    http.get(Uris.DAILY_QUOTE, buildDailyQuoteRoute(quotesService, gson))
    http.get(Uris.WEEK_QUOTES, buildWeekQuotesRoute(quotesService, gson))

    println("Ready and listening on port $port. Press any key to terminate")
    readln()
    http.stop()
}

/**
 * Builds the handler for GET operations on the home resource.
 */
fun buildHomeRoute(service: QuotesService, gson: Gson) = { request: Request, response: Response ->
    log(request)
    response.type(MediaType.Siren.toString())
    response.header("cache-control", "public, max-age=${secondsTilEndOfDay()}")
    val result = service
        .getQuoteForToday()
        .toSirenEntity(
            links = listOf(
                SirenLink(rel = listOf("self"), href = Uris.home(request.baseUrl)),
                SirenLink(rel = listOf("week"), href = Uris.weekQuotes(request.baseUrl)),
                SirenLink(rel = listOf("daily"), href = Uris.dailyQuote(request.baseUrl))
            )
        )
    gson.toJson(result)
}

/**
 * Builds the handler for GET operations on the daily quote resource.
 */
fun buildDailyQuoteRoute(service: QuotesService, gson: Gson) = { request: Request, response: Response ->
    log(request)

    response.type(MediaType.Siren.toString())
    response.header("cache-control", "public, max-age=${secondsTilEndOfDay()}")
    val result = service
        .getQuoteForToday()
        .toSirenEntity(
            links = listOf(
                SirenLink(rel = listOf("home"), href = Uris.home(request.baseUrl)),
                SirenLink(rel = listOf("self"), href = Uris.dailyQuote(request.baseUrl)),
                SirenLink(rel = listOf("week"), href = Uris.weekQuotes(request.baseUrl)),
            )
        )
    gson.toJson(result)
}

/**
 * Builds the handler for GET operations on the week quotes resource.
 */
fun buildWeekQuotesRoute(service: QuotesService, gson: Gson) = { request: Request, response: Response ->
    log(request)
    response.type(MediaType.Siren.toString())
    response.header("cache-control", "public, max-age=${secondsTilEndOfDay()}")
    val result = service
        .getWeeksQuotes()
        .toSirenEntity(
            baseUrl = request.baseUrl,
            links = listOf(
                SirenLink(rel = listOf("self"), href = Uris.weekQuotes(request.baseUrl)),
                SirenLink(rel = listOf("home"), href = Uris.home(request.baseUrl)),
            )
        )
    gson.toJson(result)
}

fun log(request: Request) {
    logger.info("Servicing request ${request.info()}")
    logger.info("Request headers are ${request.headers()}")
    logger.info("if-modified-since = ${request.headers("if-modified-since")}")
    logger.info("cache-control = ${request.headers("cache-control")}")

    val header = request.headers("if-modified-since") ?: ""
    if (header.isNotBlank()) {
        val parsed = LocalDateTime.parse(header, DateTimeFormatter.RFC_1123_DATE_TIME)
        logger.info("if-modified-since parsed = $parsed")
    }

}

val Request.baseUrl: String
    get() = "${scheme()}://${host()}"
fun Request.info() = "${requestMethod()} ${url()}"
