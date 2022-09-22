package palbp.laboratory.demos.quoteofday.main.views

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.demos.quoteofday.R
import palbp.laboratory.demos.quoteofday.TAG

enum class LoadingState { Idle, Loading }

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    state: LoadingState = LoadingState.Idle,
) {
    Log.i(TAG, "LoadingButton: composing")
    Button(
        onClick = onClick,
        enabled = state == LoadingState.Idle,
        modifier = modifier.testTag("LoadingButton")
    ) {
        val buttonTextId =
            if (state == LoadingState.Idle) R.string.fetch_button_text_idle
            else R.string.fetch_button_text_loading
        Text(text = stringResource(id = buttonTextId))
    }
}

@Preview
@Composable
fun LoadingButtonPreview() {
    LoadingButton()
}