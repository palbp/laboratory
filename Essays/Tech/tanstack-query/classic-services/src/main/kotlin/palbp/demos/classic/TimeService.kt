package palbp.demos.classic

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class TimeService {

    private val logger: Logger = LoggerFactory.getLogger(TimeService::class.java)

    @GetMapping("/api/time")
    fun getTime(): TimeOutputModel = with(LocalDateTime.now()) {
        TimeOutputModel(hour, minute, second).also {
            logger.info("Returning time: $it")
        }
    }

    data class TimeOutputModel(
        val hours: Int,
        val minutes: Int,
        val seconds: Int
    )
}