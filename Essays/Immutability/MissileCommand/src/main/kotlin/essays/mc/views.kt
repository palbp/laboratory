package essays.mc

import pt.isel.canvas.Canvas
import pt.isel.canvas.GREEN
import pt.isel.canvas.RED
import pt.isel.canvas.WHITE
import kotlin.math.roundToInt

const val MISSILE_LENGTH = 12.0
const val MISSILE_TRAIL_LENGTH = 120.0


/**
 * Draws the game, that is, everything that is visible on the screen.
 */
fun drawGame(canvas: Canvas, game: Game) {
    canvas.erase()
    drawGround(canvas, game)
    drawWorld(canvas, game.world)
}

/**
 * Draws the world.
 * @param canvas    the canvas where the world is to be drawn
 * @param world     the world
 */
fun drawWorld(canvas: Canvas, world: World) {
    world.explosions.forEach { drawExplosion(canvas, it) }
    world.missiles.forEach { drawMissile(canvas, it) }
    world.defenderMissiles.forEach { drawMissile(canvas, it) }
}

/**
 * Draws an explosion.
 * @param canvas        the canvas where the explosion is to be drawn
 * @param explosion     the explosion
 */
private fun drawExplosion(canvas: Canvas, explosion: Explosion) {
    canvas.drawCircle(
        xCenter = explosion.center.x.toInt(),
        yCenter = explosion.center.y.toInt(),
        radius = explosion.radius.toInt(),
        color = explosion.color
    )
}

/**
 * Draws a missile.
 * @param canvas    the canvas where the missile is to be drawn
 * @param missile   the missile
 */
private fun drawMissile(canvas: Canvas, missile: Missile) {

    val missileVectorNorm = missile.velocity.toVector().norm()

    val missileVectorView = missileVectorNorm * MISSILE_LENGTH
    val missileStart = Location(missile.current.x, missile.current.y)
    val missileEnd = (missileStart.toVector() - missileVectorView).toLocation()

    canvas.drawLine(
        xFrom = missileStart.x.roundToInt(),
        yFrom = missileStart.y.roundToInt(),
        xTo = missileEnd.x.roundToInt(),
        yTo = missileEnd.y.roundToInt(),
        color = WHITE,
        thickness = 3
    )

    val missileTrailVector = missileVectorNorm * MISSILE_TRAIL_LENGTH
    val projectedTrailEnd = missileEnd.toVector() - missileTrailVector

    val distanceToOrigin = (missileEnd.toVector() - missile.origin.toVector()).magnitude()
    val distanceToTrailEnd = (missileEnd.toVector() - projectedTrailEnd).magnitude()

    val trailEnd =
        if (distanceToTrailEnd < distanceToOrigin) projectedTrailEnd.toLocation()
        else missile.origin

    canvas.drawLine(
        xFrom = missileEnd.x.roundToInt(),
        yFrom = missileEnd.y.roundToInt(),
        xTo = trailEnd.x.roundToInt(),
        yTo = trailEnd.y.roundToInt(),
        color = if (missile.type == MissileType.ATTACKER) RED else GREEN,
        thickness = 1
    )
}

/**
 * Draws the ground, including the information regarding the current game mode
 * @param canvas    the canvas where the ground is to be drawn
 * @param game      the game instance
 */
fun drawGround(canvas: Canvas, game: Game) {
    canvas.drawLine(
        xFrom = 0,
        yFrom = game.world.height - game.world.groundHeight,
        xTo = game.world.width,
        yTo = game.world.height - game.world.groundHeight,
        color = GREEN,
        thickness = 4
    )

    val modeText = when {
        game.mode == Mode.PLAYING -> "REC"
        game.replayInfo.mode == ReplayMode.FORWARD -> "\u25B6"
        game.replayInfo.mode == ReplayMode.BACKWARD -> "\u25C0"
        else -> ""
    }

    canvas.drawText(
        x = game.world.width - 100,
        y = game.world.height - game.world.groundHeight / 3,
        txt = modeText,
        color = GREEN,
        fontSize = 24
    )
}











