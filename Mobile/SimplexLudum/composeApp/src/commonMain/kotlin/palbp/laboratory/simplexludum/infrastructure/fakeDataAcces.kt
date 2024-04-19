package palbp.laboratory.simplexludum.infrastructure

import kotlinx.coroutines.delay
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform

/**
 * Fake implementation of the data access function responsible for getting the
 * existing game lists
 */
suspend fun getFakeGameLists(): List<GameListSummary> {
    delay(1000L)
    return listOf(
        GameListSummary("Platinum", 19),
        GameListSummary("Completed", 36),
        GameListSummary("Backlog", 23),
        GameListSummary("Wishlist", 10),
        GameListSummary("Collections", 3)
    )
}

/**
 * Fake implementation of the data access function responsible for getting the
 * latest games
 */
suspend fun getFakeLatestGames(): List<Game> {
    delay(1000L)
    return listOf(
        Game(
            name = "Elden Ring",
            developer = "FromSoftware",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS5,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Demon's Souls",
            developer = "FromSoftware",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS5,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Bloodborne",
            developer = "FromSoftware",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Dragon's Dogma: Dark Arisen",
            developer = "Capcom",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS3,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Dragon's Dogma: Dark Arisen",
            developer = "Capcom",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS4,
            distribution = Distribution.DIGITAL
        ),
        Game(
            name = "Dragon's Dogma II",
            developer = "Capcom",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS5,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Blasphemous",
            developer = "The Game Kitchen",
            genres = setOf(Genre.ADVENTURE, Genre.RPG, Genre.PLATFORM),
            platform = Platform.PS4,
            distribution = Distribution.SUBSCRIPTION
        ),
        Game(
            name = "Dark Souls: Remastered",
            developer = "FromSoftware",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS4,
            distribution = Distribution.DIGITAL
        ),
        Game(
            name = "Blasphemous II",
            developer = "The Game Kitchen",
            genres = setOf(Genre.ADVENTURE, Genre.RPG, Genre.PLATFORM),
            platform = Platform.PS5,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "Resident Evil 4",
            developer = "CAPCOM Co., Ltd.",
            genres = setOf(Genre.ACTION, Genre.HORROR),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        ),
        Game(
            name = "Evil West",
            developer = "Flying Wild Dog",
            genres = setOf(Genre.ACTION, Genre.HORROR, Genre.SHOOTER),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        ),
        Game(
            name = "Nobody Saves the World",
            developer = "Drinkbox Studios",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS5,
            distribution = Distribution.SUBSCRIPTION
        ),
        Game(
            name = "Bloodstained: Ritual of the Night",
            developer = "ArtPlay",
            genres = setOf(Genre.ADVENTURE, Genre.RPG, Genre.PLATFORM),
            platform = Platform.PS4,
            distribution = Distribution.DIGITAL
        ),
        Game(
            name = "Alienation",
            developer = "Drinkbox Studios",
            genres = setOf(Genre.SHOOTER),
            platform = Platform.PS4,
            distribution = Distribution.SUBSCRIPTION
        ),
        Game(
            name = "Sea of Stars",
            developer = "Sabotage Studio",
            genres = setOf(Genre.ADVENTURE, Genre.RPG, Genre.TURN_BASED),
            platform = Platform.PS5,
            distribution = Distribution.SUBSCRIPTION
        ),
        Game(
            name = "Remnant: From the Ashes",
            developer = "Gunfire Games",
            genres = setOf(Genre.ADVENTURE, Genre.RPG, Genre.SHOOTER),
            platform = Platform.PS4,
            distribution = Distribution.SUBSCRIPTION
        ),
        Game(
            name = "Final Fantasy XVI",
            developer = "Square Enix",
            genres = setOf(Genre.ADVENTURE, Genre.RPG),
            platform = Platform.PS5,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "The Callisto Protocol",
            developer = "Striking Distance Studios",
            genres = setOf(Genre.ADVENTURE, Genre.HORROR),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        ),
    )
}

suspend fun getFakeGameListWithQuery(query: String): List<Game> =
    getFakeLatestGames().let { allGames ->
        if (query.isBlank()) allGames
        else allGames.filter { it.name.toString().contains(query, ignoreCase = true) }
    }
