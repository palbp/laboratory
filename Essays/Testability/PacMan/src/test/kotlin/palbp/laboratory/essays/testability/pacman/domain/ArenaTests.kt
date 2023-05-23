package palbp.laboratory.essays.testability.pacman.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class ArenaTests {

    @Test
    fun `changeHeroDirection changes the hero's intended direction`() {
        val sut = ArenaState(arena = createArena(), action = HeroAction.MOVE)
        val expected = sut.copy(sut.arena.copy(pacMan = sut.arena.pacMan.changeIntent(Direction.DOWN)))
        assertEquals(expected = expected, actual = sut.changeHeroDirection(Direction.DOWN))
    }

    @Test
    fun `moveHero moves the hero to the intended direction`() {
        val sut = ArenaState(arena = Arena(createMaze(), Hero(Coordinate(1, 1), Direction.RIGHT), emptyList()), action = HeroAction.MOVE)
        val actual = sut.moveHero()
        assertEquals(expected = Coordinate(1, 2), actual = actual.arena.pacMan.at)
    }
}
