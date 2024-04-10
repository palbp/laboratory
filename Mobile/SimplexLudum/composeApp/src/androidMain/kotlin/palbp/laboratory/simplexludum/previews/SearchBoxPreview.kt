package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.ui.common.SearchBox
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    SimplexLudumTheme {
        SearchBox(onSearchRequested = { })
    }
}