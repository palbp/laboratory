package palbp.laboratory.essays.testability.pacman.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HeroTests {

    private val maze = createMaze()

    @Test
    fun `hero can move to empty cell`() {
        val start = Coordinate(row = 1, column = 1)
        val sut = Hero(at = start, facing = Direction.RIGHT)
        val expected = Hero(
            at = Coordinate(row = 1, column = 2),
            facing = Direction.RIGHT,
            previouslyAt = start
        )
        assertEquals(expected = expected, actual = sut.move(maze).hero)
    }

    @Test
    fun `hero cannot move to wall`() {
        val sut = Hero(at = Coordinate(row = 1, column = 1), facing = Direction.UP)
        val expected = Hero(at = Coordinate(row = 1, column = 1), facing = Direction.UP)
        assertEquals(expected = expected, actual = sut.move(maze).hero)
    }

    @Test
    fun `hero can change direction`() {
        val sut = Hero(at = Coordinate(row = 1, column = 1), facing = Direction.UP)
        val expected = Hero(at = Coordinate(row = 1, column = 1), facing = Direction.UP, intent = Direction.RIGHT)
        assertEquals(expected = expected, actual = sut.changeIntent(Direction.RIGHT))
    }

    @Test
    fun `hero can change direction and move`() {
        val start = Coordinate(row = 1, column = 1)
        val sut = Hero(at = start, facing = Direction.UP)
        val expected = Hero(
            at = Coordinate(row = 1, column = 2),
            facing = Direction.RIGHT,
            intent = Direction.RIGHT,
            previouslyAt = start
        )
        assertEquals(expected = expected, actual = sut.changeIntent(Direction.RIGHT).move(maze).hero)
    }

    @Test
    fun `when hero moves its previous location is updated`() {
        val sut = Hero(at = Coordinate(row = 1, column = 1), facing = Direction.RIGHT)
        val expected = Hero(at = Coordinate(row = 1, column = 2), facing = Direction.RIGHT, previouslyAt = Coordinate(row = 1, column = 1))
        assertEquals(expected = expected, actual = sut.move(maze).hero)
    }
}
