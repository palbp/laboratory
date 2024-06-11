package palbp.laboratory.demos.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import palbp.laboratory.demos.navigation.ui.theme.NavigationDemoTheme

const val TAG = "NavigationDemo"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationDemoApp()
        }
    }
}

@Composable
fun NavigationDemoApp() {
    NavigationDemoTheme {
        val navigator = rememberNavController()
        NavHost(navController = navigator, startDestination = startScreenRoute) {
            composable(route = startScreenRoute) {
                StartScreen(onStartIntent = { navigator.navigate(tabbedScreenRoute) })
            }

            composable(route = tabbedScreenRoute) {
                TabbedScreen(onBackIntent = { navigator.popBackStack() })
            }
        }
    }
}
