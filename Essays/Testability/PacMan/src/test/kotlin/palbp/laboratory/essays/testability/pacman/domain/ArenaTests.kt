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
        val sut = ArenaState(arena = Arena(createMaze(), Hero(Coordinate(1, 1), Direction.RIGHT)), action = HeroAction.MOVE)
        val actual = sut.moveHero()
        assertEquals(expected = Coordinate(1, 2), actual = actual.arena.pacMan.at)
    }

    @Test
    fun `moveHero to a cell with a pellet eats the pellet`() {
        val sut = ArenaState(arena = Arena(createMaze(), Hero(Coordinate(1, 1), Direction.RIGHT)), action = HeroAction.MOVE)
        val actual = sut.moveHero()
        assertEquals(expected = Cell.EMPTY, actual = actual.arena.maze[actual.arena.pacMan.at])
        assertEquals(expected = GhostsMode.CHASE, actual = actual.ghostsMode)
        assertEquals(expected = HeroAction.EAT_PELLET, actual = actual.action)
    }

    @Test
    fun `moveHero to a cell with a power pellet eats the pellet and changes the ghosts mode to scatter`() {
        val sut = ArenaState(arena = Arena(createMaze(), Hero(Coordinate(2, 1), Direction.DOWN)), action = HeroAction.MOVE)
        val actual = sut.moveHero()
        assertEquals(expected = Cell.EMPTY, actual = actual.arena.maze[actual.arena.pacMan.at])
        assertEquals(expected = GhostsMode.SCATTER, actual = actual.ghostsMode)
        assertEquals(expected = HeroAction.EAT_POWER_PELLET, actual = actual.action)
    }

    @Test
    fun `moveHero to a cell with a wall stops the hero`() {
        val sut = ArenaState(arena = Arena(createMaze(), Hero(Coordinate(1, 1), Direction.UP)), action = HeroAction.MOVE)
        val actual = sut.moveHero()
        assertEquals(expected = Coordinate(1, 1), actual = actual.arena.pacMan.at)
        assertEquals(expected = HeroAction.STOP, actual = actual.action)
    }
}