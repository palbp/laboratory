package palbp.laboratory.simplexludum.ui

import junit.framework.TestCase.assertEquals
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform

class ResourceResolutionTests {

    @Test
    fun getDistributionResource_with_ps_digital_distribution_returns_ps_store() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS2,
            distribution = Distribution.DIGITAL
        )

        // Act
        val result = getDistributionResource(game)

        // Assert
        assertEquals(PS_STORE_RESOURCE, result)
    }

    @Test
    fun getDistributionResource_with_physical_distribution_returns_dvd() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PC,
            distribution = Distribution.PHYSICAL
        )

        // Act
        val result = getDistributionResource(game)

        // Assert
        assertEquals(DVD_RESOURCE, result)
    }

    @Test
    fun getPlatformResource_with_ps5_platform_returns_ps5() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        )

        // Act
        val result = getPlatformResource(game)

        // Assert
        assertEquals(PS5_RESOURCE, result)
    }

    @Test
    fun getPlatformResource_with_ps4_platform_returns_ps4() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = listOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )

        // Act
        val result = getPlatformResource(game)

        // Assert
        assertEquals(PS4_RESOURCE, result)
    }
}