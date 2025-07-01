package palbp.laboratory.demos.mm

import org.slf4j.LoggerFactory
import kotlin.test.Test

private val log = LoggerFactory.getLogger(MemoryModelTests::class.java)

class MemoryModelTests {
    var done = false
    var value = 0

    fun run() {
        log.info("Waiting for value. Initial value is = $value")
        while (!done)
            Thread.sleep(100)

        log.info("value = $value")
    }

    @Test
    fun `tests visibility`() {
        Thread(::run).start()

        Thread.sleep(10000)

        value = 911
        done = true

        Thread.sleep(1000)
        log.info("Test ends")
    }
}
