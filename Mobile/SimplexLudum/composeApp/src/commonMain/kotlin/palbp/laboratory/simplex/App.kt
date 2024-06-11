package palbp.laboratory.simplex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import palbp.laboratory.simplex.ui.common.theme.SimplexLudumTheme
import palbp.laboratory.simplex.ui.mycollection.MyCollectionScreen
import palbp.laboratory.simplex.ui.mycollection.myCollectionRoute
import palbp.laboratory.simplex.ui.start.StartScreen
import palbp.laboratory.simplex.ui.start.startRoute

/**
 * The application's root composable, containing the topmost navigation graph.
 */
@Composable
fun App(navigator: NavHostController = rememberNavController()) {
    SimplexLudumTheme {
        NavHost(navController = navigator, startDestination = startRoute) {
            composable(route = startRoute) {
                StartScreen(onGetStartedIntent = { navigator.navigate(myCollectionRoute) })
            }

            composable(route = myCollectionRoute) {
                MyCollectionScreen()
            }
        }
    }
}