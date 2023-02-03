package palbp.laboratory.essays.testability.repl

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ParserTests {

    @Test
    fun `parse on a string bearing a constant returns the corresponding instance`() {
        val expression = parse("42")
        assertIs<Constant>(expression)
        assertEquals(expected = 42, actual = expression.value)
    }

    @Test
    fun `parse on a string bearing an operation returns the corresponding instance`() {
        val expression = parse("+ 2 3")
        assertIs<Addition>(expression)

        val left = expression.left
        assertIs<Constant>(left)
        assertEquals(expected = 2, actual = left.value)

        val right = expression.right
        assertIs<Constant>(right)
        assertEquals(expected = 3, actual = right.value)
    }
}
