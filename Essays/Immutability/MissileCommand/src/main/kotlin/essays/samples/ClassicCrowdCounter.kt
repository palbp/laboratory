package essays.samples

import kotlin.math.max
import kotlin.math.min

class ClassicCrowdCounter(initialValue: Int = 0, val capacity: Int) {
    var value: Int = initialValue
        private set

    init {
        require(capacity > 0) { "Capacity must be positive" }
        require(initialValue in 0..capacity) { "Value must be between 0 and capacity" }
    }

    operator fun dec(): ClassicCrowdCounter {
        value = max(0, value - 1)
        return this
    }

    operator fun inc(): ClassicCrowdCounter {
        value = min(capacity, value + 1)
        return this
    }
}
