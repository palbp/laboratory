package essays.mc

/**
 * Representation of the game world. It represents, at any given time, the state of all participants.
 */
data class World(
    val width: Int = WORLD_WIDTH,
    val height: Int = WORLD_HEIGHT,
    val groundHeight: Int = GROUND_HEIGHT,
    val missiles: List<Missile> = getListOf(),
    val defenderMissiles: List<Missile> = getListOf(),
    val explosions: List<Explosion> = getListOf(),
)

/**
 * Computes the new world state based on the current state.
 */
fun World.computeNext(): World {
    val evolvedExplosions = explosions.mapNotNull { it.evolve() }
    val nonExplodedMissiles = removeExplodedMissiles(missiles, explosions)

    val movedDefenderMissiles = defenderMissiles.map { it.move() }
    val defenderMissilesToExplode = movedDefenderMissiles.filter { it.hasReachedTarget() }

    val evolvedDefenderMissiles = movedDefenderMissiles - defenderMissilesToExplode
    val explodedDefenderMissiles = defenderMissilesToExplode.map { Explosion(center = it.current) }

    val groundHitMissiles = nonExplodedMissiles.filter { it.current.y >= height - groundHeight }
    val groundExplosions = groundHitMissiles.map { Explosion(center = it.current) }

    return copy(
        missiles = nonExplodedMissiles - groundHitMissiles,
        defenderMissiles = evolvedDefenderMissiles,
        explosions = evolvedExplosions + groundExplosions + explodedDefenderMissiles
    )
}

/**
 * Adds an attacker missile to the world, returning the new world state.
 */
fun World.addMissile(): World = copy(missiles = missiles + createMissile(width, height, MARGIN))

/**
 * Adds a defender missile to the world, returning the new world state.
 */
fun World.addDefenderMissile(target: Location): World {
    val missile = createDefenderMissile(
        origin = Location(width / 2.0, (height - groundHeight).toDouble()),
        target = target,
        DEFENDER_MISSILE_VELOCITY_MAGNITUDE
    )

    return copy(defenderMissiles = defenderMissiles + missile)
}

private const val WORLD_WIDTH = 800
private const val WORLD_HEIGHT = 600
private const val MARGIN = 50
private const val GROUND_HEIGHT = 50

/**
 * Computes the list of missiles from [missiles] that were destroyed by any of the [explosions].
 */
private fun removeExplodedMissiles(missiles: List<Missile>, explosions: List<Explosion>): List<Missile> {
    return missiles.filter { !detectCollision(explosions, it) }.map { it.move() }
}

/**
 * Verifies if any of the given [explosions] destroy the [missile].
 */
private fun detectCollision(explosions: List<Explosion>, missile: Missile): Boolean {
    return explosions.any { detectCollision(it, missile) }
}

/**
 * Verifies if the [explosion] destroys the [missile] or not.
 */
private fun detectCollision(explosion: Explosion, missile: Missile) =
    explosion.isExpanding() && distance(explosion.center, missile.current) < explosion.radius
