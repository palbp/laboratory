package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import org.slf4j.LoggerFactory
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private val logger = LoggerFactory.getLogger("Flows 101")

@OptIn(ExperimentalTime::class)
class FlowsBasicsTests {

    private suspend fun getInt(value: Int): Int {
        delay(1000)
        return value
    }

    private suspend fun getInts(): Flow<Int> {
        logger.info("getInts()")
        return flow {
            repeat(10) {
                val value = getInt(it)
                logger.info("Emitting $value")
                emit(value)
            }
        }
    }

    @Test
    fun `basic flow test`() {

        val duration = measureTime {
            runBlocking {
                val dataFlow: Flow<Int> = getInts()

                dataFlow.filter {
                    logger.info("Checking if $it remains in the flow")
                    it % 2 == 0
                }.map {
                    logger.info("Mapping $it to String")
                    it.toString()
                }.collect {
                    logger.info("============> Collected $it from the flow")
                    delay(1000)
                }
            }
        }

        logger.info("Test took $duration")
    }

    @Test
    fun `basic sequence test`() {

        fun getIntSequence(): Sequence<Int> {
            return sequence {
                repeat(10) {
                    logger.info("Yielding $it")
                    yield(it)
                    Thread.sleep(1000)
                }
            }
        }

        val duration = measureTime {
            val sequence = getIntSequence()
            sequence
                .filter {
                    logger.info("Checking if $it remains in the sequence")
                    it % 2 == 0
                }.map {
                    logger.info("Mapping $it to String")
                    it.toString()
                }.forEach {
                    logger.info("============> Collected $it from the sequence")
                }
        }

        logger.info("Test took $duration")
    }
}