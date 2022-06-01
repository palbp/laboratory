package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import kotlin.test.Test

private val logger = LoggerFactory.getLogger("Coroutines 101")

class CoroutinesBasicsTests {

    @Test
    fun `coroutines scheduling`() {
        logger.info("Test starts")
        runBlocking {
            logger.info("Creating coroutines")
            repeat(5) {
                launch {
                    logger.info("Coroutine starts")
                    delay(2000)
                    logger.info("Coroutine resumes for a while")
                    delay(2000)
                    logger.info("Coroutine ends")
                }
            }
        }
        logger.info("Test ends")
    }

    @Test
    fun `suspending vs blocking`() {
        logger.info("Test starts")
        runBlocking {
            logger.info("Creating coroutines")
            repeat(2) {
                launch {
                    logger.info("Coroutine starts")
                    delay(2000)
                    logger.info("Coroutine ends")
                }
            }
        }
        logger.info("Test ends")
    }

   @Test
   fun `join with parent waits for children`() {
        runBlocking {
            val parent = launch {
                logger.info("Parent starts and launches children")
                repeat(3) {
                    launch {
                        logger.info("Child starts")
                        delay(5000)
                        logger.info("Child ends")
                    }
                }
                logger.info("Parent ends")
            }

            logger.info("runBlocking before parent.join")
            parent.join()
            logger.info("runBlocking after parent.join")
        }
    }

    @Test
    fun `uncaught exception cancels children`() {
        val job = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher()).launch {
            val parent = launch {
                try {
                    logger.info("Parent starts and launches children")
                    repeat(3) {
                        launch {
                            try {
                                logger.info("Child starts")
                                delay(5000)
                                if (it == 1)
                                    throw Exception("Booom")
                                logger.info("Child ends")
                            } catch (cancelled: CancellationException) {
                                logger.info("Child cancelled")
                            }
                        }
                    }

                    delay(10000)
                }
                catch (e: CancellationException) {
                    logger.info("Parent cancelled")
                }
            }
        }

        runBlocking {
            logger.info("runBlocking before job.join")
            job.join()
            logger.info("runBlocking after job.join")
        }
    }
}