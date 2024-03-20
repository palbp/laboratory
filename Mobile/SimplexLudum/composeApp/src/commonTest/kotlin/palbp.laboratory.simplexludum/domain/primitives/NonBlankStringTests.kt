package palbp.laboratory.simplexludum.domain.primitives

import palbp.laboratory.simplexludum.domain.primitives.NonBlankString
import kotlin.test.Test
import kotlin.test.assertFailsWith


class NonBlankStringTests {

    @Test
    fun `create NonBlankString with blank value fails`() {
        assertFailsWith<IllegalArgumentException> {
            val blankValue = "   \t  "
            NonBlankString(blankValue)
        }
    }

    @Test
    fun `create NonBlankString with empty name fails`() {
        assertFailsWith<IllegalArgumentException> {
            val emptyValue = ""
            NonBlankString(emptyValue)
        }
    }
}