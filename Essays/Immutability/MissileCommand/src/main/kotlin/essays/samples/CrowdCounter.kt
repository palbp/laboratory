package essays.samples

data class CrowdCounter(val value: Int = 0, val capacity: Int) {
    init {
        require(capacity > 0) { "Capacity must be positive" }
        require(value in 0..capacity) { "Value must be between 0 and capacity" }
    }

    operator fun inc(): CrowdCounter = if (value < capacity) copy(value = value + 1) else this
    operator fun dec(): CrowdCounter = if (value > 0) copy(value = value - 1) else this
}
