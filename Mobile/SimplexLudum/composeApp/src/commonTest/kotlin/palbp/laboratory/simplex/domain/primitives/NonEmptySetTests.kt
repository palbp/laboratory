package palbp.laboratory.simplex.domain.primitives

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NonEmptySetTests {

    @Test
    fun `create NonEmptySet with empty set fails`() {
        assertFailsWith<IllegalArgumentException> {
            val emptySet = emptySet<String>()
            NonEmptySet(emptySet)
        }
    }

    @Test
    fun `create NonEmptySet with non empty set succeeds`() {
        val nonEmptySet = setOf("a", "b", "c")
        NonEmptySet(nonEmptySet)
    }

    @Test
    fun `add element to NonEmptySet returns set with element added`() {
        val nonEmptySet = NonEmptySet(setOf("a", "b", "c"))
        val newSet: NonEmptySet<String> = nonEmptySet + "d"
        assertTrue(newSet.contains("d"))
    }
}