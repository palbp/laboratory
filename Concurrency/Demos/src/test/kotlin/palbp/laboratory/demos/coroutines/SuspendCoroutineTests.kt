package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.test.Test

private val logger = LoggerFactory.getLogger("suspendCoroutine Tests")
private val pool = Executors.newSingleThreadScheduledExecutor()

fun simulateAsyncReadLine(): CompletableFuture<String> {
    val operation = CompletableFuture<String>()
    pool.schedule({ operation.complete("Some input") }, 2, TimeUnit.SECONDS)
    return operation
}

suspend fun simulateReadLine(): String {
    val future = simulateAsyncReadLine()
    return suspendCoroutine { continuation ->
        future.thenAccept {
            continuation.resume(it)
        }
    }
}

class SuspendCoroutineTests {

    @Test
    fun `adapt to future based API`() {
        logger.info("Test starts")
        runBlocking {
            val input = simulateReadLine()
            logger.info("Got $input")
        }
        logger.info("Test ends")
    }
}