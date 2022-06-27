package palbp.laboratory.codelabs.basiclayouts

import alignYourBodyPreviewData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.codelabs.basiclayouts.ui.theme.BasicLayoutsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicLayoutsCodelabTheme {
                BasicLayoutsCodelabAppScreen()
            }
        }
    }
}

@Composable
fun BasicLayoutsCodelabAppScreen() {
    Scaffold(bottomBar = { BasicLayoutsCodeLabBottomNavigation() }) {
        paddingValues ->
            HomeScreen(
                alignYourBodyData = alignYourBodyPreviewData,
                favoriteCollectionsData = favoriteCollectionsPreviewData,
                modifier = Modifier.padding(paddingValues)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicLayoutsCodelabTheme {
        BasicLayoutsCodelabAppScreen()
    }
}