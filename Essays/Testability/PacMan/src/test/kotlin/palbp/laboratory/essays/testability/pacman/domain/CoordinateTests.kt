package palbp.laboratory.essays.testability.pacman.domain

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateTests {
    @Test
    fun `new instance with negative row fails`() {
        assertThrows<IllegalArgumentException> {
            Coordinate(row = -1, column = 0)
        }
    }

    @Test
    fun `new instance with negative column fails`() {
        assertThrows<IllegalArgumentException> {
            Coordinate(row = 0, column = -1)
        }
    }

    @Test
    fun `new instance with linear index has correct values`() {
        val sut = Coordinate(index = MAZE_WIDTH)
        assertEquals(expected = 0, actual = sut.column)
        assertEquals(expected = 1, actual = sut.row)
    }

    @Test
    fun `new instance must have a row within the arena's bounds`() {
        assertThrows<IllegalArgumentException> {
            Coordinate(row = MAZE_HEIGHT, column = 0)
        }
    }

    @Test
    fun `new instance must have a column within the arena's bounds`() {
        assertThrows<IllegalArgumentException> {
            Coordinate(row = 1, column = MAZE_WIDTH)
        }
    }
}
