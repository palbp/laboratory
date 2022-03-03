package palbp.laboratory.imageviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import palbp.laboratory.imageviewer.filters.*
import java.io.File

/**
 * Composable that defines the application's main window.
 * @param onCloseRequested  The function to be called when the user intends to close the window
 */
@Composable
fun MainWindow(onCloseRequested: () -> Unit) = Window(onCloseRequest = onCloseRequested, title = "Image Viewer") {

    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val isGrayScale = remember { mutableStateOf(false) }
    val adjustBrightness = remember { mutableStateOf(false) }

    fun loadImage(file: File) {
        file.inputStream().use {
            // NOTE: This is wrong! It MUST be done without blocking the calling thread, because it is called from
            // an event handler. Because all events are delivered sequentially, if we block the calling thread, the
            // handling of all subsequent events is delayed and therefore the user experience suffers.
            print("Loading image ... ")
            imageBitmap.value = loadImageBitmap(it)
            println("done!")
        }

    }

    MaterialTheme {
        MainWindowMenu(
            onQuit = onCloseRequested,
            onLoad = { openImageFilePicker(onImageFilePicked = { if (it != null) loadImage(it) }) },
            isGrayScaleEnabled = isGrayScale.value,
            onGrayScaleChanged = { isGrayScale.value = it },
            isBrightnessEnabled = adjustBrightness.value,
            onBrightnessChanged = { adjustBrightness.value = it },
        )
        Box(modifier = Modifier.fillMaxSize()) {
            val currentImage = imageBitmap.value
            if (currentImage != null) {
                // IMPORTANT NOTE: This is wrong too!
                // Intensive compute bound work should be off-loaded to other threads. In this case, we are delaying
                // composition, which can happen many times. This is true in both versions, ST and MT.
                // We will fix this before the semester ends! =)
                val imageToDisplay = if (isGrayScale.value) convertToGrayScaleMT(currentImage) else currentImage
                val finalImage = if (adjustBrightness.value) adjustBrightnessMT(imageToDisplay, 0.2f) else imageToDisplay
                Image(finalImage, "", modifier = Modifier.fillMaxSize())
            }
        }
    }
}

/**
 * The application's main window menu
 */
@Composable
fun FrameWindowScope.MainWindowMenu(
    onLoad: () -> Unit,
    onQuit: () -> Unit,
    isGrayScaleEnabled: Boolean,
    onGrayScaleChanged: (Boolean) -> Unit,
    isBrightnessEnabled: Boolean,
    onBrightnessChanged: (Boolean) -> Unit
) = MenuBar {
    Menu("File") {
        Item(text = "Open", onClick = onLoad)
        Separator()
        Item(text = "Quit", onClick = onQuit)
    }
    Menu("Image") {
        CheckboxItem(text = "Grayscale", checked = isGrayScaleEnabled, onCheckedChange = onGrayScaleChanged)
        CheckboxItem(text = "Brightness", checked = isBrightnessEnabled, onCheckedChange = onBrightnessChanged)
    }
}