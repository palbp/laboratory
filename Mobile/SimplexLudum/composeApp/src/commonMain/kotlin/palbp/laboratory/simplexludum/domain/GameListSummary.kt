package palbp.laboratory.simplexludum.domain

/**
 * Represents the summary of a list of games on our domain.
 * @property name the name of the list of games. It must be unique.
 * @property size the size of the list. Cannot be negative.
 */
data class GameListSummary(val name: NonBlankString, val size: Int) {
    init {
        require(size >= 0) { "Size cannot be negative" }
    }
    constructor(name: String, size: Int) : this(NonBlankString(name), size)
}
