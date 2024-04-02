package palbp.laboratory.simplexludum.domain.primitives

/**
 * Represents a non empty set of elements.
 * @property set the actual set
 */
class NonEmptySet<T>(private val set: Set<T>) : Set<T> by set {
    init {
        require(set.isNotEmpty()) { "Set cannot be empty" }
    }
}

operator fun <T> NonEmptySet<T>.plus(element: T): NonEmptySet<T> =
    NonEmptySet(this as Set<T> + element)
