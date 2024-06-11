package palbp.laboratory.simplex.ui.start

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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.app_motto
import simplexludum.composeapp.generated.resources.app_name
import simplexludum.composeapp.generated.resources.get_started
import simplexludum.composeapp.generated.resources.logo

// TAGS used to identify semantically relevant parts of the UI
const val GET_STARTED_BUTTON_TAG = "get-started-button"
const val START_SCREEN_TAG = "start-screen"

/**
 * The Start screen UI.
 * @param onGetStartedIntent The action to take when the get started button is clicked.
 */
@Composable
fun StartScreenView(onGetStartedIntent: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag(START_SCREEN_TAG)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.4f)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(Res.string.app_motto),
                style = MaterialTheme.typography.titleMedium,
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
            Text(text = stringResource(Res.string.get_started))
        }
    }
}

