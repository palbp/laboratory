package palbp.laboratory.essays.testability.pacman.view

import kotlin.test.Test
import kotlin.test.assertEquals

class LayoutTests {
    @Test
    fun `layoutSpriteIndexToPoint returns correct coordinates`() {
        val sut = layoutSpriteIndexToPoint(index = 18)
        assertEquals(expected = Point(x = (LAYOUT_SPRITE_SIZE + 1) * 2, y = LAYOUT_SPRITE_SIZE + 1), actual = sut)
    }
}
