package palbp.laboratory.simplexludum.domain

/**
 * Represents a non empty list of elements.
 * @property list the actual list
 */
class NonEmptyList<T>(private val list: List<T>) : List<T> by list {
    init {
        require(list.isNotEmpty()) { "List cannot be empty" }
    }
}

operator fun <T> NonEmptyList<T>.plus(element: T): NonEmptyList<T> =
    NonEmptyList(this as List<T> + element)
