package palbp.laboratory.simplexludum.ui.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * See https://m3.material.io/styles/color/roles for more information about
 * color roles in Material Design 3.
 */

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme(
    primary = LightRed,
    onSecondaryContainer = LightRed,
    onSurface = DarkRed,
    primaryContainer = LightGray,
    surface = LightGray,
    surfaceTint = LightGray,
    secondaryContainer = LightGray,
)

@Composable
fun SimplexLudumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}