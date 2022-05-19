package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
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
}