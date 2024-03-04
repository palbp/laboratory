package palbp.laboratory.simplexludum.ui.common

import palbp.laboratory.simplexludum.ui.start.APP_MOTTO_TEXT
import palbp.laboratory.simplexludum.ui.start.APP_TITLE
import palbp.laboratory.simplexludum.ui.start.GET_STARTED_TEXT
import palbp.laboratory.simplexludum.ui.mycollection.LATEST_TITLE
import palbp.laboratory.simplexludum.ui.mycollection.MY_COLLECTION_TITLE
import palbp.laboratory.simplexludum.ui.mycollection.SEE_ALL

/**
 * Temporary utility to be replaced once we have a proper resource management
 * provided by Compose.
 */
fun stringResource(resourceId: String): String {
    return when (resourceId) {
        APP_TITLE -> "Simplex\nLudum"
        APP_MOTTO_TEXT -> "Keep track of your video games collection. The easy way."
        GET_STARTED_TEXT -> "Get Started"
        MY_COLLECTION_TITLE -> "My Collection"
        LATEST_TITLE -> "Latest"
        SEE_ALL -> "See all"
        else -> "Unknown resource"
    }
}
