package palbp.laboratory.simplexludum.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplexludum.ui.common.theme.SimplexLudumTheme
import palbp.laboratory.simplexludum.ui.mycollection.MyCollectionScreen

@Preview(showBackground = true, showSystemUi = true, name = "Home Tab")
@Composable
fun MyCollectionScreenPreview() {
    SimplexLudumTheme {
        MyCollectionScreen().Content()
    }
}