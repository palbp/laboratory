package essays.mc

import pt.isel.canvas.WHITE

const val MIN_RADIUS = 6.0
const val MAX_RADIUS = 36.0

const val GROWTH_RATE = 1.06
const val SHRINK_RATE = 0.94

/**
 * Represents explosions at a given time instant.
 *
 * @property center     the explosion's center
 * @property radius     the explosion's current radius
 * @property rate       the explosion's changing rate (i.e. >= 1.0, it is growing)
 * @property color      the explosion's color
 */
data class Explosion(
    val center: Location,
    val radius: Double = MIN_RADIUS,
    val rate: Double = GROWTH_RATE,
    val color: Int = WHITE
)

/**
 * Verifies if this explosion is growing or not.
 */
fun Explosion.isExpanding(): Boolean = rate == GROWTH_RATE

/**
 * Makes this explosion evolve by expanding until it reaches the maximum radius. Once
 * that radius is reached, the explosion starts to contract until it reaches its minimum radius. After that,
 * it disappears.
 * @return the new evolved explosion, or null if it has disappeared
 */
fun Explosion.evolve(): Explosion? {
    val newExplosion = if (isExpanding()) expand() else contract()
    return when {
        newExplosion.radius <= MIN_RADIUS -> null
        newExplosion != this -> newExplosion
        else -> revertExplosionRate(newExplosion)
    }
}

/**
 * Makes this explosion vary according to its explosion rate if [predicate] evaluates to true. Otherwise, it returns
 * the explosion as it is.
 */
private fun Explosion.maybeApplyExplosionRate(predicate: (Explosion) -> Boolean) =
    if (predicate(this)) copy(radius = radius * rate)
    else this

/**
 * Conditionally expands this explosion if it hasn't reached the maximum radius
 */
private fun Explosion.expand(): Explosion = maybeApplyExplosionRate { it.radius < MAX_RADIUS }

/**
 * Conditionally contracts this explosion if it hasn't reached the minimum radius yet.
 */
private fun Explosion.contract(): Explosion = maybeApplyExplosionRate { it.radius > MIN_RADIUS }

/**
 * Reverts the given explosion's rate, that is, it is expanding, and we make it contract.
 *
 * @param explosion the explosion
 * @return the new reverted explosion
 */
private fun revertExplosionRate(explosion: Explosion) =
    Explosion(explosion.center, explosion.radius, SHRINK_RATE, explosion.color)
