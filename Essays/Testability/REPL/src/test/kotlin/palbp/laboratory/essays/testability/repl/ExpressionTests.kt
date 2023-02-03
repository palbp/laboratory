package palbp.laboratory.essays.testability.repl

import kotlin.test.Test
import kotlin.test.assertEquals

class ExpressionTests {

    @Test
    fun `evaluate a Constant produces its value`() {
        val value = 4
        val sut = Constant(value)
        assertEquals(expected = value, actual = evaluate(sut))
    }

    @Test
    fun `evaluate an Operation produces its result`() {
        val sut = Addition(Constant(3), Constant(4))
        assertEquals(expected = 7, actual = evaluate(sut))
    }

    @Test
    fun `evaluate an expression produces its result`() {
        val sut = parse(input = "* + 2 3 + 3 1")
        assertEquals(expected = 20, actual = evaluate(sut))
    }
}
