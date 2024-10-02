package essays.mc

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represents coordinates on the game arena.
 * @property x  the horizontal coordinate
 * @property y  the vertical coordinate
 */
data class Location(val x: Double, val y: Double) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
}

/**
 * Converts a [Location] to a [Vector2D].
 */
fun Location.toVector() = Vector2D(this.x, this.y)

/**
 * Converts a [Vector2D] to a [Location]
 */
fun Vector2D.toLocation() = Location(this.x, this.y)

/**
 * Represents coordinates' variations in the arena for the time interval corresponding to the chosen FPS.
 * @property dx the horizontal coordinate variation
 * @property dy the vertical coordinate variation
 */
data class Velocity(val dx: Double, val dy: Double)

/**
 * Converts a [Velocity] to a [Vector2D]
 */
fun Velocity.toVector() = Vector2D(dx, dy)

/**
 * Converts a [Vector2D] to a [Velocity]
 */
fun Vector2D.toVelocity() = Velocity(x, y)

/**
 * Computes the distance between two locations.
 */
fun distance(l1: Location, l2: Location): Double = sqrt((l1.x - l2.x).pow(2) + (l1.y - l2.y).pow(2))

/**
 * Adds to this location the displacement expressed by [velocity], returning the new location.
 */
operator fun Location.plus(velocity: Velocity): Location = Location(this.x + velocity.dx, this.y + velocity.dy)
