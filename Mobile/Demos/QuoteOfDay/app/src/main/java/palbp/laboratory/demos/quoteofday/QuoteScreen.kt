package palbp.laboratory.demos.quoteofday

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                QuoteView(quote = quote)
                LoadingButton(state = loadingState, onClick = onUpdateRequest)
            }
        }
    }
}

@Composable
fun QuoteView(quote: Quote?) {
    Column(modifier = Modifier.padding(64.dp)) {
        if (quote != null) {
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