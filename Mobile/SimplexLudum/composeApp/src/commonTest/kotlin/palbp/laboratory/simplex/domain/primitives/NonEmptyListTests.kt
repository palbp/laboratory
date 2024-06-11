package palbp.laboratory.simplex.domain.primitives

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NonEmptyListTests {

    @Test
    fun `create NonEmptyList with empty list fails`() {
        assertFailsWith<IllegalArgumentException> {
            val emptyList = emptyList<String>()
            NonEmptyList(emptyList)
        }
    }

    @Test
    fun `create NonEmptyList with non empty list succeeds`() {
        val nonEmptyList = listOf("a", "b", "c")
        NonEmptyList(nonEmptyList)
    }

    @Test
    fun `add element to NonEmptyList returns list with element added`() {
        val nonEmptyList = NonEmptyList(listOf("a", "b", "c"))
        val newList: NonEmptyList<String> = nonEmptyList + "d"
        assertTrue(newList.contains("d"))
    }
}