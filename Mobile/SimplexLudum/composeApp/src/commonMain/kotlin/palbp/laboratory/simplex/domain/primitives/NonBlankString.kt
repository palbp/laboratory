package palbp.laboratory.simplex.domain.primitives

/**
 * Represents non blank strings on our domain.
 */
data class NonBlankString(val value: String) {
    init {
        require(value.isNotBlank()) { "String cannot be blank" }
    }
    override fun toString(): String = value
}

fun String.toNonBlankOrThrow(): NonBlankString = NonBlankString(this)
