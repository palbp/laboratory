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
        GameListSummary("Wish List", 10),
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
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "name2",
            developer = "developer1",
            genres = setOf(Genre.ACTION),
            platform = Platform.PS5,
            distribution = Distribution.SUBSCRIPTION
        )
    )
}
