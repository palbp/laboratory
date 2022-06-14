package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.test.Test

private val logger = LoggerFactory.getLogger("suspendCoroutine Tests")
private val pool = Executors.newSingleThreadScheduledExecutor()

fun simulateAsyncReadLine(input: String): CompletableFuture<String> {
    val operation = CompletableFuture<String>()
    pool.schedule({ operation.complete(input) }, 10, TimeUnit.SECONDS)
    return operation
}

suspend fun <T> CompletableFuture<T>.await(): T {
    logger.info("CompletableFuture<T>.await() starts")
    return suspendCancellableCoroutine { continuation ->
        logger.info("suspendCancellableCoroutine starts")
        whenComplete { result, error ->
            logger.info("whenComplete start and coroutine is cancelled = ${continuation.isCancelled}")
            if (error != null)
                continuation.resumeWithException(error)
            else
                continuation.resume(result)
        }
    }
}

suspend fun simulateReadLine(input: String): String = simulateAsyncReadLine(input).await()

class SuspendCoroutineTests {

    @Test
    fun `adapt to future based API`() {
        logger.info("Test starts")
        runBlocking {
            val input = simulateReadLine("the input")
            logger.info("Got $input")
        }
        logger.info("Test ends")
    }

    @Test
    fun `suspendCancellableCoroutine is cancellable`() {
        logger.info("Test starts")
        runBlocking {
            logger.info("Father starts")
            val child = launch {
                logger.info("Child starts")
                logger.info("Got ${simulateReadLine("the first input")}")
                delay(5000)
                logger.info("Got ${simulateReadLine("the second input")}")
                logger.info("Child ends")
            }
            delay(3000)
            logger.info("Father cancels child after a delay")
            child.cancelAndJoin()
            logger.info("Father ends")
        }
        logger.info("Test ends")
    }
}