package palbp.laboratory.simplex.domain

import kotlin.test.Test
import kotlin.test.assertFailsWith

class GameListSummaryTests {
    @Test
    fun `create GameListSummary with negative size fails`() {
        assertFailsWith<IllegalArgumentException> {
            GameListSummary(name = "name", size = -1)
        }
    }

    @Test
    fun `create GameListSummary with positive size succeeds`() {
        GameListSummary(name = "name", size = 1)
    }
}