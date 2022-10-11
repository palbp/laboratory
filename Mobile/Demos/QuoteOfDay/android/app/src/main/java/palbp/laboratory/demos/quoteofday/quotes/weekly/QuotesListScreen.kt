package palbp.laboratory.demos.quoteofday.quotes.weekly

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.demos.quoteofday.quotes.Quote
import palbp.laboratory.demos.quoteofday.ui.TopBar
import palbp.laboratory.demos.quoteofday.ui.theme.QuoteOfDayTheme

data class QuotesListScreenState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = true
)

@Composable
fun QuotesListScreen(
    state: QuotesListScreenState = QuotesListScreenState(),
    onBackRequested: () -> Unit = { },
    onUpdateRequest: (() -> Unit)? = null,
    onInfoRequest: (() -> Unit)? = null,
) {
    QuoteOfDayTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                TopBar(
                    onBackRequested = onBackRequested,
                    onInfoRequested = onInfoRequest
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onUpdateRequest ?: { },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                //TODO()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun QuotesListScreenPreview() {
    QuotesListScreen(QuotesListScreenState())
}
