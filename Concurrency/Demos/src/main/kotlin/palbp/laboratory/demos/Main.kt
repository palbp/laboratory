package palbp.laboratory.demos

import org.slf4j.LoggerFactory

var done = false
var value = 0

private val log = LoggerFactory.getLogger("Main")

fun run() {
    log.info("Waiting for value. Initial value is = $value")
    while (!done)
        Thread.sleep(100)

    log.info("value = $value")
}

fun main() {

    log.info("Main starts")

    Thread(::run).start()

    Thread.sleep(10000)

    value = 911
    done = true

    Thread.sleep(1000)
    log.info("Main ends")
}