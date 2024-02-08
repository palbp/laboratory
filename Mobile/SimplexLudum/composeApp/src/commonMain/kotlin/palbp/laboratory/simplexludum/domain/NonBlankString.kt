package palbp.laboratory.simplexludum.domain

data class NonBlankString(val value: String) {
    init {
        require(value.isNotBlank()) { "String cannot be blank" }
    }
    override fun toString(): String = value
}

fun String.toNonBlankOrThrow(): NonBlankString = NonBlankString(this)
