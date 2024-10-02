package essays.mc

import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2DTests {

    @Test
    fun `magnitude of a known vector is correctly computed`() {
        val vector = Vector2D(3.0, 4.0)
        assertEquals(expected = 5.0, actual = vector.magnitude())
    }

    @Test
    fun `magnitude for a null vector is correctly computed`() {
        val vector = Vector2D(0.0, 0.0)
        assertEquals(expected = 0.0, actual = vector.magnitude())
    }

    @Test
    fun `magnitude for a known vector with negative coordinates is correctly computed`() {
        val vector = Vector2D(-4.0, -3.0)
        assertEquals(expected = 5.0, actual = vector.magnitude())
    }

    @Test
    fun `magnitude of a vector with a single non-null coordinate is correctly computed`() {
        val vector = Vector2D(0.0, 8.0)
        assertEquals(expected = 8.0, actual = vector.magnitude())
    }

    // TODO: This seems to be a good example for using property-based testing
}