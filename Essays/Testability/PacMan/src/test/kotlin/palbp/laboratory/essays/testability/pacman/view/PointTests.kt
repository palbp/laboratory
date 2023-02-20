package palbp.laboratory.essays.testability.pacman.view

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class PointTests {
    @Test
    fun `points must have non-negative x values`() {
        assertThrows<IllegalArgumentException> {
            Point(x = -1, y = 0)
        }
    }

    @Test
    fun `points must have non-negative y values`() {
        assertThrows<IllegalArgumentException> {
            Point(x = 3, y = -2)
        }
    }
}
