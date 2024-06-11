package palbp.laboratory.simplex.ui.common

import kotlin.test.assertEquals
import kotlin.test.Test
import palbp.laboratory.simplex.domain.Distribution
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.Genre
import palbp.laboratory.simplex.domain.Platform
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.dvd
import simplexludum.composeapp.generated.resources.ps4
import simplexludum.composeapp.generated.resources.ps5
import simplexludum.composeapp.generated.resources.ps_store

class ResourceResolutionTests {

    @Test
    fun getDistributionResource_with_ps_digital_distribution_returns_ps_store() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS2,
            distribution = Distribution.DIGITAL
        )

        // Act
        val result = getDistributionResource(game)

        // Assert
        assertEquals(Res.drawable.ps_store, result)
    }

    @Test
    fun getDistributionResource_with_physical_distribution_returns_dvd() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PC,
            distribution = Distribution.PHYSICAL
        )

        // Act
        val result = getDistributionResource(game)

        // Assert
        assertEquals(Res.drawable.dvd, result)
    }

    @Test
    fun getPlatformResource_with_ps5_platform_returns_ps5() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS5,
            distribution = Distribution.DIGITAL
        )

        // Act
        val result = getPlatformResource(game)

        // Assert
        assertEquals(Res.drawable.ps5, result)
    }

    @Test
    fun getPlatformResource_with_ps4_platform_returns_ps4() {
        // Arrange
        val game = Game(
            name = "name",
            developer = "developer",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        )

        // Act
        val result = getPlatformResource(game)

        // Assert
        assertEquals(Res.drawable.ps4, result)
    }
}