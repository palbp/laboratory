package palbp.laboratory.demos.async

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertTrue

val logger: Logger = LoggerFactory.getLogger("Async IO Tests")

class AsyncIOTests {

    private fun asyncOperation(data: String): CompletionStage<String> {
        val timer = Timer()
        val operation = CompletableFuture<String>()
        timer.schedule(object : TimerTask() {
                override fun run() {
                    logger.info("AsyncOperation completes")
                    operation.completeExceptionally(Exception("O VAR tem olho de dragão... ;p"))
                    //operation.complete(data.uppercase())
                }
            },
            3000
        )
        return operation
    }

    @Test
    fun `makes http request and processes result`() {
        logger.info("Test starts")
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI("https://httpbinz.org/delay/1000"))
            .build()

        logger.info("Before client.sendAsync()")
        val asyncOperation = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        logger.info("After client.sendAsync()")
        val chain = asyncOperation
            .thenApply {
                logger.info("Inside asyncOperation.thenAccept with response ${it.body()}")
                it.body().uppercase()
            }
            .exceptionally {
                logger.error("Inside asyncOperation.exceptionally", it)
                "error"
            }
            .whenComplete { _, _ ->
                logger.info("Inside asyncOperation.whenComplete")
            }
        logger.info("After setting up asyncOperation processing chain")
        logger.info("Chain result is ${chain.get()}")
        logger.info("Test ends")
    }

    @Test
    fun `chains two async operations and processes result`() {
        logger.info("Test starts")
        val done = CountDownLatch(1)
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI("https://httpbin.org/delay/1000"))
            .build()

        logger.info("Before client.firstAsyncOperation()")
        val firstAsyncOperation = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        logger.info("After client.firstAsyncOperation()")
        val chain = firstAsyncOperation
            .thenCompose {
                logger.info("Inside firstAsyncOperation.thenCompose")
                asyncOperation(it.body())
            }
            .exceptionally {
                logger.error("Inside chain.exceptionally", it)
                "error"
            }
        chain.thenAccept {
            logger.info("Inside chain.thenAcceptAsync with $it")
        }
        .whenComplete { _, _ ->
            done.countDown()
        }

        logger.info("After setting up async processing chain")
        assertTrue(done.await(60, TimeUnit.SECONDS))
        logger.info("Test ends")
    }
}