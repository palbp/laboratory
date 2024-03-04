package palbp.laboratory.simplexludum.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.domain.isPlaystationVariant

const val DVD_RESOURCE = "dvd.png"
const val PS_STORE_RESOURCE = "ps_store.png"
const val PS_PLUS_RESOURCE = "ps_plus.png"
const val PS1_RESOURCE = "ps1.png"
const val PS2_RESOURCE = "ps2.png"
const val PS3_RESOURCE = "ps3.png"
const val PS4_RESOURCE = "ps4.png"
const val PS5_RESOURCE = "ps5.png"

/**
 * Returns the resource for the distribution of the game
 */
fun getDistributionResource(game: Game): String = when {
    isPlaystationVariant(game.platform) && game.distribution == Distribution.SUBSCRIPTION -> PS_PLUS_RESOURCE
    isPlaystationVariant(game.platform) && game.distribution == Distribution.DIGITAL -> PS_STORE_RESOURCE
    game.distribution == Distribution.PHYSICAL -> DVD_RESOURCE
    else -> DVD_RESOURCE
}

/**
 * Returns the resource for the platform of the game
 */
fun getPlatformResource(game: Game): String = when (game.platform) {
    Platform.PS1 -> PS1_RESOURCE
    Platform.PS2 -> PS2_RESOURCE
    Platform.PS3 -> PS3_RESOURCE
    Platform.PS4 -> PS4_RESOURCE
    Platform.PS5 -> PS5_RESOURCE
    else -> PS1_RESOURCE
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getDistributionResourcePainter(game: Game) = painterResource(getDistributionResource(game))

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getPlatformResourcePainter(game: Game) = painterResource(getPlatformResource(game))