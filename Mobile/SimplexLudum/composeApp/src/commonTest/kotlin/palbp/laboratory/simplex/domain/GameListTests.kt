package palbp.laboratory.simplex.domain

import kotlin.test.Test
import kotlin.test.assertEquals

private val listOfGames = listOf(
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
        genres = setOf(Genre.ADVENTURE),
        platform = Platform.PS5,
        distribution = Distribution.SUBSCRIPTION
    ),
)

class GameListTests {

    @Test
    fun `size of GameList returns size of content`() {
        // Arrange
        val listOfGames = listOfGames
        // Act
        val gameList = GameList(name = "name", content = listOfGames)
        // Assert
        assertEquals(expected = 2, gameList.size)
    }
}