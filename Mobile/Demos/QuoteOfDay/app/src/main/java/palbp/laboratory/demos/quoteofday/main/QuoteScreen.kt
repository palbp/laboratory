package palbp.laboratory.demos.quoteofday.main

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import palbp.laboratory.demos.quoteofday.TAG
import palbp.laboratory.demos.quoteofday.main.views.LoadingButton
import palbp.laboratory.demos.quoteofday.main.views.LoadingState
import palbp.laboratory.demos.quoteofday.main.views.QuoteView
import palbp.laboratory.demos.quoteofday.ui.theme.QuoteOfDayTheme

@Composable
fun QuoteOfDayScreen(
    quote: Quote? = null,
    loadingState: LoadingState = LoadingState.Idle,
    onUpdateRequest: () -> Unit = { }
) {
    Log.i(TAG, "QuoteOfDayScreen: composing")
    QuoteOfDayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Log.i(TAG, "QuoteOfDayScreen content: composing")
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                if (quote != null)
                    QuoteView(quote = quote)
                LoadingButton(state = loadingState, onClick = onUpdateRequest)
            }
        }
    }
}

private val loremIpsum = LoremIpsum(words = 40)

private val loremIpsumQuote = Quote(
    text = loremIpsum.values.joinToString { it },
    author = "Cicero"
)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun QuoteOfDayScreenPreview() {
    QuoteOfDayScreen(loremIpsumQuote)
}