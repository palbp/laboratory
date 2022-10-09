package pt.isel.pdm.quote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.LoggerFactory
import pt.isel.pdm.quote.daily.FakeQuotesService
import pt.isel.pdm.quote.daily.QuotesService
import pt.isel.pdm.quote.daily.toSirenEntity
import pt.isel.pdm.quote.hypermedia.MediaType
import pt.isel.pdm.quote.utils.MediaTypeTypeAdapter
import spark.Request
import spark.Response
import spark.Service.ignite

private const val DEFAULT_PORT = 8080

private val logger = LoggerFactory.getLogger("Quote-Of-Day server")

/**
 * The server's entry point.
 */
fun main(args: Array<String>) {

    println("Quote-Of-Day server is starting ...")
    val port = if (args.size > 1) args[1].toIntOrNull() ?: DEFAULT_PORT else DEFAULT_PORT

    val quotesService = FakeQuotesService(minutesInADay = 5)
    val gson = GsonBuilder()
        .registerTypeAdapter(MediaType::class.java, MediaTypeTypeAdapter())
        .create()
    val http = ignite().port(port)

    http.get("/", buildHomeRoute(quotesService, gson))

    println("Ready and listening on port $port. Press any key to terminate")
    readln()
    http.stop()
}

/**
 * Builds the handler for GET operations on the home resource.
 */
fun buildHomeRoute(service: QuotesService, gson: Gson) = { request: Request, response: Response ->
    logger.info("Servicing request ${request.info()}")
    response.type(MediaType.Siren.toString())
    gson.toJson(service.getQuoteForToday().toSirenEntity())
}

fun Request.info() = "${requestMethod()} ${pathInfo()}"