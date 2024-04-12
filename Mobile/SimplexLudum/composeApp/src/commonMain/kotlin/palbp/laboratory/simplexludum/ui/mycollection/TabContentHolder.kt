package palbp.laboratory.simplexludum.ui.mycollection

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val ContentPadding = compositionLocalOf { PaddingValues() }

/**
 * Voyagers Lib Tab navigation does not propagate Scaffold's padding values to the content of the tab.
 * It also does not propagate any modifiers to the content of the tab.
 * THe issue is already raised here: https://github.com/adrielcafe/voyager/issues/26
 * This composable is a placeholder used to pass the padding values to the content of the tab
 * through the Composition Local API.
 * @param paddingValues The padding values to apply to the content of the tab
 * @param content The content of the tab
 */
@Composable
fun TabContentHolder(paddingValues: PaddingValues, content: @Composable () -> Unit) {
    CompositionLocalProvider(ContentPadding provides paddingValues) {
        content()
    }
}
