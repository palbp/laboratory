package palbp.laboratory.simplexludum.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTests {

    @Test
    fun `isPlaystationVariant with ps5 platform returns true`() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        )
        // Act
        val result = isPlaystationVariant(game.platform)
        // Assert
        assertTrue(result)
    }

    @Test
    fun `isPlaystationVariant with ps4 platform returns true`() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.DIGITAL
        )
        // Act
        val result = isPlaystationVariant(game.platform)
        // Assert
        assertTrue(result)
    }

    @Test
    fun `isPlaystationVariant with pc platform returns false`() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PC,
            distribution = Distribution.DIGITAL
        )
        // Act
        val result = isPlaystationVariant(game.platform)
        // Assert
        assertFalse(result)
    }
}