package palbp.laboratory.simplexludum.domain

/**
 * Represents a list of games on our domain
 * @property name the name of the list of games. It must be unique.
 * @property content the actual content
 */
data class GameList(val name: NonBlankString, val content: List<Game>) {
    constructor(name: String, content: List<Game>) : this(name = NonBlankString(name), content)
}

/**
 * Returns the size of the list of games
 */
val GameList.size: Int
    get() = content.size
