package palbp.laboratory.codelabs.composebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.codelabs.composebasics.ui.theme.ComposeBasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsCodelabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnBoarding)
        OnBoardingScreen(onContinueClicked = { shouldShowOnBoarding = false })
    else
        Greetings()
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsCodelabTheme {
        MyApp()
    }
}