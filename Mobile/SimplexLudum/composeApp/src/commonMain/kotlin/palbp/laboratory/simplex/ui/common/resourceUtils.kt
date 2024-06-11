package palbp.laboratory.simplex.ui.common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import palbp.laboratory.simplex.domain.Distribution
import palbp.laboratory.simplex.domain.Game
import palbp.laboratory.simplex.domain.isPlaystationVariant
import palbp.laboratory.simplex.domain.Platform
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.dvd
import simplexludum.composeapp.generated.resources.ps1
import simplexludum.composeapp.generated.resources.ps2
import simplexludum.composeapp.generated.resources.ps3
import simplexludum.composeapp.generated.resources.ps4
import simplexludum.composeapp.generated.resources.ps5
import simplexludum.composeapp.generated.resources.ps_plus
import simplexludum.composeapp.generated.resources.ps_store

/**
 * Returns the resource for the distribution of the game
 */
fun getDistributionResource(game: Game): DrawableResource = when {
    isPlaystationVariant(game.platform) && game.distribution == Distribution.SUBSCRIPTION -> Res.drawable.ps_plus
    isPlaystationVariant(game.platform) && game.distribution == Distribution.DIGITAL -> Res.drawable.ps_store
    game.distribution == Distribution.PHYSICAL -> Res.drawable.dvd
    else -> Res.drawable.dvd
}

/**
 * Returns the resource for the platform of the game
 */
fun getPlatformResource(game: Game): DrawableResource = when (game.platform) {
    Platform.PS1 -> Res.drawable.ps1
    Platform.PS2 -> Res.drawable.ps2
    Platform.PS3 -> Res.drawable.ps3
    Platform.PS4 -> Res.drawable.ps4
    Platform.PS5 -> Res.drawable.ps5
    else -> Res.drawable.ps1
}

@Composable
fun getDistributionResourcePainter(game: Game) = painterResource(getDistributionResource(game))

@Composable
fun getPlatformResourcePainter(game: Game) = painterResource(getPlatformResource(game))