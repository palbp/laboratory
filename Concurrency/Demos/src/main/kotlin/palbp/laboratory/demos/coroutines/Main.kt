package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val logger: Logger = LoggerFactory.getLogger("Coroutines")

fun main() {
    runBlocking {
        val count = 2
        logger.info("Inside runBlocking: starting $count coroutines ")
        repeat(count) {
            launch(CoroutineName("${it + 1}"), start = CoroutineStart.DEFAULT) {
                val name = this.coroutineContext[CoroutineName]?.name
                logger.info("Running coroutine $name")
            }
        }
        logger.info("Inside runBlocking: started $count coroutines.")

        delay(10000)
        logger.info("Cancelling scope.")
        this.cancel()
    }

    logger.info("Bye!")
}
