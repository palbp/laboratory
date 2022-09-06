package palbp.laboratory.demos.quoteofday

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

enum class LoadingState { Idle, Loading }

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: LoadingState = LoadingState.Idle,
) {
    Log.i(TAG, "LoadingButton: composing")
    Button(onClick = onClick, modifier = modifier) {
        val buttonTextId =
            if (state == LoadingState.Idle) R.string.fetch_button_text_idle
            else R.string.fetch_button_text_loading
        Text(text = stringResource(id = buttonTextId))
    }
}