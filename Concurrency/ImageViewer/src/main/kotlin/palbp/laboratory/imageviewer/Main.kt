package palbp.laboratory.imageviewer

import androidx.compose.ui.window.application

/**
 * The application's entry point
 */
fun main() = application {
    MainWindow(onCloseRequested = ::exitApplication)
}
