package palbp.laboratory.demos.quoteofday.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.demos.quoteofday.R
import palbp.laboratory.demos.quoteofday.ui.theme.QuoteOfDayTheme

/**
 * Used to aggregate [TopBar] navigation handlers.
 */
data class NavigationHandlers(
    val onBackRequested: (() -> Unit)? = null,
    val onHistoryRequested: (() -> Unit)? = null,
    val onInfoRequested: (() -> Unit)? = null,
)

@Composable
fun TopBar(navigation: NavigationHandlers = NavigationHandlers()) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (navigation.onBackRequested != null) {
                IconButton(onClick = navigation.onBackRequested) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        },
        actions = {
            if (navigation.onHistoryRequested != null) {
                IconButton(onClick = navigation.onHistoryRequested) {
                    Icon(Icons.Default.List, contentDescription = "Localized description")
                }
            }
            if (navigation.onInfoRequested != null) {
                IconButton(onClick = navigation.onInfoRequested) {
                    Icon(Icons.Default.Info, contentDescription = "Localized description")
                }
            }
        }
    )}

@Preview
@Composable
private fun TopBarPreviewInfoAndHistory() {
    QuoteOfDayTheme {
        TopBar(
            NavigationHandlers(onInfoRequested = { }, onHistoryRequested = { })
        )
    }
}

@Preview
@Composable
private fun TopBarPreviewBackAndInfo() {
    QuoteOfDayTheme {
        TopBar(
            NavigationHandlers(onBackRequested = { }, onInfoRequested = { })
        )
    }
}

@Preview
@Composable
private fun TopBarPreviewBack() {
    QuoteOfDayTheme {
        TopBar(NavigationHandlers(onBackRequested = { }))
    }
}
