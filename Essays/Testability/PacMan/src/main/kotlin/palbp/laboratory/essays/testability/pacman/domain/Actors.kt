package palbp.laboratory.essays.testability.pacman.domain

/**
 * The hero's starting position in the arena.
 * Whenever Pac-Man spawns, he does it at the exact same location.
 */
val pacManStartingPosition = Coordinate(row = 23, column = 13)

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

/**
 * The game's hero, Pac-Man
 */
data class Hero(val at: Coordinate, val facing: Direction)

/**
 * Gets a new hero instance at the same location as [hero] but facing [to].
 */
fun faceTo(hero: Hero, to: Direction) = hero.copy(facing = to)
