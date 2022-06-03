package palbp.laboratory.demos.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Flows 101")

class FlowsBasicsTests {

    @Test
    fun test() {

        runBlocking {
            val dataFlow: Flow<Int> = flow {
                repeat(10) {
                    emit(it)
                    delay(2000)
                }
            }

            dataFlow.collect {
                logger.info("Collected $it from the flow")
            }
        }
    }
}