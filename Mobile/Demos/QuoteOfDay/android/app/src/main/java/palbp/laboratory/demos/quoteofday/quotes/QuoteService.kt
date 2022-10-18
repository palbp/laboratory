package palbp.laboratory.demos.quoteofday.quotes

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import palbp.laboratory.demos.quoteofday.utils.hypermedia.SirenLink
import palbp.laboratory.demos.quoteofday.utils.hypermedia.SirenMediaType
import palbp.laboratory.demos.quoteofday.utils.send
import java.net.URL

/**
 * Contract to be supported by quote service implementations. This abstraction's
 * purpose is mostly to facilitate automated testing.
 */
interface QuoteService {

    /**
     * Gets the daily quote
     */
    suspend fun fetchQuote(): Quote

    /**
     * Gets the week's quotes
     */
    suspend fun fetchWeekQuotes(): List<Quote>
}

/**
 * Actual implementation of the quote service that is responsible for obtaining
 * the quotes from the remote web API. Because the API is hypermedia driven,
 * the service discovers the existing resource links by navigating the APIs
 * responses in search of the corresponding links.
 */
class RealQuoteService(
    private val quoteHome: URL,
    private val httpClient: OkHttpClient,
    private val jsonEncoder: Gson
) : QuoteService {

    override suspend fun fetchQuote(): Quote {
        val request = Request.Builder()
            .url(quoteHome)
            .build()

        val quoteDto = request.send(httpClient) { response ->
            val contentType = response.body?.contentType()
            if (response.isSuccessful && contentType != null && contentType == SirenMediaType) {
                jsonEncoder.fromJson<QuoteDto>(
                    response.body?.string(),
                    QuoteDtoType.type
                )
            }
            else {
                throw UnexpectedResponseException(response = response)
            }
        }

        weekQuotesLink = getWeekQuotesLink(quoteDto)
        return Quote(quoteDto)
    }

    override suspend fun fetchWeekQuotes(): List<Quote> {

        val weekQuotesURL: URL = ensureWeekQuotesLink()

        val request = Request.Builder()
            .url(weekQuotesURL)
            .build()

        val quotes = request.send(httpClient) { response ->

            val contentType = response.body?.contentType()
            if (response.isSuccessful && contentType != null && contentType == SirenMediaType) {
                jsonEncoder.fromJson<QuoteListDto>(
                    response.body?.string(),
                    QuoteListDtoType.type
                )
            }
            else {
                throw UnexpectedResponseException(response = response)
            }
        }

        return quotes.toQuoteList()
    }

    /**
     * The link for the APIs resource bearing the week's quotes, or null if not yet discovered
     */
    private var weekQuotesLink: SirenLink? = null

    /**
     * Navigates [quoteDto] in search of the link for the APIs resource
     * bearing the week's quotes.
     * @return the link found in the DTO, or null
     */
    private fun getWeekQuotesLink(quoteDto: QuoteDto) =
        quoteDto.links?.find { it.rel.contains("week") }

    /**
     * Makes sure we have the required link, if necessary, by navigating again through the
     * APIs responses, starting at the home resource (the quote of the day, in this case).
     *
     * @return the [URL] instance representing the link for the week's quotes
     * @throws [UnresolvedLinkException] if the link could not be found
     */
    private suspend fun ensureWeekQuotesLink(): URL {
        if (weekQuotesLink == null) {
            fetchQuote()
        }
        val link = weekQuotesLink ?: throw UnresolvedLinkException()
        return link.href.toURL()
    }
}

/**
 * Exception throw when a required navigation link could not be found by
 * the service implementation in the APIs responses.
 */
class UnresolvedLinkException(msg: String = "") : Exception(msg)

/**
 * Exception throw when an unexpected response was received from the API.
 */
class UnexpectedResponseException(
    val response: Response
) : Exception("Unexpected ${response.code} response from the API.")
