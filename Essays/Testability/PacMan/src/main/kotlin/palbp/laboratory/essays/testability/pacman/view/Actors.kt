package palbp.laboratory.essays.testability.pacman.view

import palbp.laboratory.essays.testability.pacman.domain.Direction
import palbp.laboratory.essays.testability.pacman.domain.Hero
import pt.isel.canvas.Canvas

/**
 * The size of each element in the actors sprite sheet (see resources/actors-sprite.png)
 * Note that there are larger elements on the sprite sheet, but they are not in use.
 */
private const val ACTORS_SPRITE_SIZE = 16

/**
 * The scaled actor size on the screen
 */
private const val ACTOR_SIZE = (ACTORS_SPRITE_SIZE * SCALE).toInt()

/**
 * Actors are drawn using an offset, so they seem to closely fit the arena's corridors
 */
private val actorsOffset = Point(x = CELL_SIZE / 2, y = CELL_SIZE / 2)

/**
 * Draws the hero on the arena.
 * @param canvas    the canvas where to draw
 * @param hero      the hero to be drawn
 */
fun drawHero(canvas: Canvas, hero: Hero) {
    val spriteSheetRow = when (hero.facing) {
        Direction.RIGHT -> 0
        Direction.LEFT -> 1
        Direction.UP -> 2
        Direction.DOWN -> 3
    }
    val originInSprite = Point(x = ACTORS_SPRITE_SIZE, y = spriteSheetRow * CELL_SIZE)
    val originInArena = Point(x = hero.at.column * CELL_SIZE - actorsOffset.x, y = hero.at.row * CELL_SIZE - actorsOffset.y)
    val spriteInfo = "${originInSprite.x},${originInSprite.y},$ACTORS_SPRITE_SIZE,$ACTORS_SPRITE_SIZE"
    canvas.drawImage(
        fileName = "actors-sprite|$spriteInfo",
        xLeft = originInArena.x,
        yTop = originInArena.y,
        width = ACTOR_SIZE,
        height = ACTOR_SIZE
    )
}
