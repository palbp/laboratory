package palbp.laboratory.simplexludum.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import palbp.laboratory.simplexludum.stringResource
import palbp.laboratory.simplexludum.ui.theme.SimplexLudumTheme

const val GET_STARTED_BUTTON_TAG = "get-started-button"

// Resource identifiers
const val APP_TITLE: String = "app_title"
const val APP_MOTTO_TEXT: String = "app_motto"
const val GET_STARTED_TEXT: String = "get_started"

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StartScreen(onGetStartedIntent: () -> Unit) {
    SimplexLudumTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.fillMaxSize().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource("sl-logo.png"),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.4f)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(APP_TITLE),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(APP_MOTTO_TEXT),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Button(
                onClick = onGetStartedIntent,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .padding(bottom = 48.dp)
                    .sizeIn(minHeight = 52.dp)
                    .testTag(GET_STARTED_BUTTON_TAG)
            ) {
                Text(text = stringResource(GET_STARTED_TEXT))
            }
        }
    }
}

/**
 * Implementation of the Voyager navigation contract
 */
object StartScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        StartScreen(
            onGetStartedIntent = { navigator.push(MyCollectionScreen) }
        )
    }
}

