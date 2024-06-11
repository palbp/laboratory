package palbp.laboratory.simplex.domain

import palbp.laboratory.simplex.domain.primitives.NonBlankString
import palbp.laboratory.simplex.domain.primitives.NonEmptySet

/**
 * Represents a game on our domain. If we want to represent a game in
 * multiple platforms, then distinct instances of this class should be created.
 * @property name the name of the game.
 * @property developer the developer of the game.
 * @property genres the genres of the game.
 * @property platform the platform where the game is available.
 * @property distribution the type of distribution of the game.
 */
data class Game(
    val name: NonBlankString,
    val developer: NonBlankString,
    val genres: NonEmptySet<Genre>,
    val platform: Platform,
    val distribution: Distribution
) {
    constructor(
        name: String,
        developer: String,
        genres: Set<Genre>,
        platform: Platform,
        distribution: Distribution
    ) : this(
        name = NonBlankString(name),
        developer = NonBlankString(developer),
        genres = NonEmptySet(genres),
        platform = platform,
        distribution = distribution
    )
}

/**
 * Represents the supported game platforms.
 */
enum class Platform {
    A500, PC, PS1, PS2, PS3, PS4, PS5, SWITCH, WII
}

/**
 * Returns true if the platform is a PlayStation variant.
 */
fun isPlaystationVariant(platform: Platform) =
    platform == Platform.PS5 ||
    platform == Platform.PS4 ||
    platform == Platform.PS3 ||
    platform == Platform.PS2 ||
    platform == Platform.PS1

/**
 * Represents the game genres. A game can have multiple genres.
 */
enum class Genre {
    ACTION,
    ADVENTURE,
    ARCADE,
    FIGHTING,
    PLATFORM,
    PUZZLE,
    RACING,
    RPG,
    HORROR,
    SHOOTER,
    SIMULATION,
    SPORTS,
    STRATEGY,
    SURVIVAL,
    TURN_BASED,
    INDIE,
    OTHER
}

/**
 * Represents the type of distribution of a game.
 */
enum class Distribution { PHYSICAL, DIGITAL, SUBSCRIPTION }
