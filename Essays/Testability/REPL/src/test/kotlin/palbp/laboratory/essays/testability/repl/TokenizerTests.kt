package palbp.laboratory.essays.testability.repl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class TokenizerTests {
    @Test
    fun `tokens must be non empty strings`() {
        assertThrows<IllegalArgumentException> {
            Token(container = "  ", startsAt = 0)
        }
    }

    @Test
    fun `tokens must have a non negative starting position`() {
        assertThrows<IllegalArgumentException> {
            Token(container = "+", startsAt = -1)
        }
    }

    @Test
    fun `tokenize on a string with no separators returns single token`() {
        val tokens = tokenize("42")
        assertEquals(expected = 1, actual = tokens.size)
        assertEquals(expected = 0, tokens.first().range.first)
    }

    @Test
    fun `tokenize on a string with whitespace separators returns list of tokens`() {
        val tokens = tokenize(" +  42   24   ")
        assertEquals(expected = 3, actual = tokens.size)
        assertEquals(expected = 1, actual = tokens[0].range.first)
        assertEquals(expected = 4, actual = tokens[1].range.first)
        assertEquals(expected = 9, actual = tokens[2].range.first)
    }
}
