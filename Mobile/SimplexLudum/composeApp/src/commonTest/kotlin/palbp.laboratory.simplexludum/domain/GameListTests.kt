package palbp.laboratory.simplexludum.domain

import kotlin.test.Test
import kotlin.test.assertEquals

private val listOfGames = listOf(
    Game(name = "name1", developer = "developer1", genres = listOf(Genre.ACTION)),
    Game(name = "name2", developer = "developer2", genres = listOf(Genre.ACTION))
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