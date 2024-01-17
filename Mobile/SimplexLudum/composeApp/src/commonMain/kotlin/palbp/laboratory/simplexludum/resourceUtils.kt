package palbp.laboratory.simplexludum

/**
 * Temporary utility to be replaced once we have a proper resource management
 * provided by Compose.
 */
fun stringResource(resourceId: String): String {
    return when (resourceId) {
        APP_TITLE -> "Simplex\nLudum"
        APP_MOTTO_TEXT -> "Keep track of your video games collection. The easy way."
        GET_STARTED_TEXT -> "Get Started"
        else -> "Unknown resource"
    }
}