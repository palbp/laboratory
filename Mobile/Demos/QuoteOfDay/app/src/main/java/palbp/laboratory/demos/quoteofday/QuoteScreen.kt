package palbp.laboratory.demos.quoteofday

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
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

@Composable
fun QuoteView(quote: Quote) {
    Log.i(TAG, "QuoteView: composing")
    Column(modifier = Modifier.padding(64.dp).testTag("QuoteView")) {
        Text(
            text = quote.text,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = quote.author,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuoteViewPreview() {
    QuoteView(aQuote)
}

private val loremIpsum = LoremIpsum(words = 40)

private val loremIpsumQuote = Quote(
    text = loremIpsum.values.joinToString { it },
    author = "Cicero"
)

private val aQuote = Quote(
    text = "O poeta é um fingidor.\n" +
        "Finge tão completamente\n" +
        "Que chega a fingir que é dor\n" +
        "A dor que deveras sente.",
    author = "Fernando Pessoa"
)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun QuoteOfDayScreenPreview() {
    QuoteOfDayScreen(loremIpsumQuote)
}