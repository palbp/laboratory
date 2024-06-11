package palbp.laboratory.simplex.previews.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplex.ui.common.theme.SimplexLudumTheme
import palbp.laboratory.simplex.ui.start.StartScreen

@Preview
@Composable
fun StartScreenPreview() {
    SimplexLudumTheme {
        StartScreen(onGetStartedIntent = { })
    }
}
