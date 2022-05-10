package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

val log = LoggerFactory.getLogger("Coroutines")

fun main() {
    runBlocking {
        val count = 6
        log.info("Inside runBlocking: starting $count coroutines ")

        repeat(count) {
            launch(CoroutineName("${it+1}")) {
                log.info("Running coroutine ${this.coroutineContext[CoroutineName]}")
                delay(5000)
                log.info("Coroutine ${it+1} ends")
            }
        }
        log.info("Inside runBlocking: started $count coroutines ")
    }

    log.info("Bye!")
}