package palbp.laboratory.demos.quoteofday.daily

import android.util.Log
import okhttp3.*
import palbp.laboratory.demos.quoteofday.TAG
import java.io.IOException
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface QuoteService {
    suspend fun fetchQuote(): Quote
}

class RealQuoteService(private val quoteHome: URL) : QuoteService {

    private val client by lazy { OkHttpClient() }

    override suspend fun fetchQuote(): Quote {
        val request = Request.Builder()
            .url(quoteHome)
            .build()

        Log.v(TAG, "fetchQuote: before suspendCoroutine in Thread = ${Thread.currentThread().name}")
        val quote = suspendCoroutine { continuation ->

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.v(TAG, "fetchQuote: onFailure in Thread = ${Thread.currentThread().name}")
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v(TAG, "fetchQuote: onResponse in Thread = ${Thread.currentThread().name}")
                    continuation.resume(Quote("Yeahh", "Cool Kid"))
                }
            })

        }

        Log.v(TAG, "fetchQuote: after suspendCoroutine in Thread = ${Thread.currentThread().name}")
        return quote
    }
}