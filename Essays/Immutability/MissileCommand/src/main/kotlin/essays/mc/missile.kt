package essays.mc

const val FOE_MISSILE_VELOCITY_MAGNITUDE = 1.6
const val DEFENDER_MISSILE_VELOCITY_MAGNITUDE = 10.0

/**
 * The two types of missiles.
 */
enum class MissileType { DEFENDER, ATTACKER }

/**
 * Represents enemy missiles.
 * @property origin     The location from where the missile was launched
 * @property target     The location to where the missile is headed
 * @property current    The missile's current location
 * @property velocity   The missile's velocity
 * @property type       The missile's type
 */
data class Missile(
    val origin: Location,
    val target: Location,
    val current: Location,
    val velocity: Velocity = Velocity(0.0, 0.0),
    val type: MissileType
)

/**
 * Moves this missile, returning the new instance.
 */
fun Missile.move(): Missile = copy(current = current + velocity)

/**
 * Checks whether the missile has reached its target.
 */
fun Missile.hasReachedTarget(): Boolean =
    if (velocity.dy > 0) current.y >= target.y
    else current.y <= target.y

/**
 * Creates a new missile with the specified constraints
 * @param worldHeight   The width of the world
 * @param worldHeight   The height of the world
 * @param dmzMargin     The width of the demilitarized zone (where no missiles will fall)
 * @return the newly created missile
 */
fun createMissile(worldWidth: Int, worldHeight: Int, dmzMargin: Int): Missile {
    val entry = Vector2D((dmzMargin..worldWidth - dmzMargin).random().toDouble(), 0.0)
    val target = Vector2D((dmzMargin..worldWidth - dmzMargin).random().toDouble(), worldHeight.toDouble())
    val velocity = ((target - entry).norm() * FOE_MISSILE_VELOCITY_MAGNITUDE).toVelocity()
    return Missile(
        origin = entry.toLocation(),
        target = target.toLocation(),
        current = entry.toLocation(),
        velocity = velocity,
        type = MissileType.ATTACKER
    )
}

/**
 * Creates a new defender missile with the given properties
 * @param origin    the missile origin (i.e. to location from where it was fired)
 * @param target    the missile's target (i.e. to location to which it was fired)
 * @param magnitude The magnitude of the missile's velocity vector
 */
fun createDefenderMissile(origin: Location, target: Location, magnitude: Double) = Missile(
    origin = origin,
    target = target,
    current = origin,
    velocity = ((target.toVector() - origin.toVector()).norm() * magnitude).toVelocity(),
    type = MissileType.DEFENDER
)
