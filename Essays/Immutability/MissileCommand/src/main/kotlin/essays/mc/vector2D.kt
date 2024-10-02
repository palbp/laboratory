package essays.mc

import kotlin.math.pow
import kotlin.math.sqrt

/**
 *  Represents vectors in a plane (2D space).
 */
data class Vector2D(val x: Double, val y: Double)

/**
 * Adds [other] vector to this one, returning the new vector.
 */
operator fun Vector2D.plus(other: Vector2D) = Vector2D(this.x + other.x, this.y + other.y)

/**
 * Subtracts [other] vector from this vector instance, returning the new vector.
 */
operator fun Vector2D.minus(other: Vector2D) = Vector2D(this.x - other.x, this.y - other.y)

/**
 * Computes the vector magnitude.
 */
fun Vector2D.magnitude(): Double = sqrt(x.pow(2) + y.pow(2))

/**
 * Computes the vector's norm.
 */
fun Vector2D.norm(): Vector2D = this / magnitude()

/**
 * Multiplies this vector with scalar [value], returning the new scaled vector.
 */
operator fun Vector2D.times(value: Double) = Vector2D(x * value, y * value)

/**
 * Divides this vector by scalar [value], returning the new scaled vector.
 */
operator fun Vector2D.div(value: Double) = Vector2D(x / value, y / value)
