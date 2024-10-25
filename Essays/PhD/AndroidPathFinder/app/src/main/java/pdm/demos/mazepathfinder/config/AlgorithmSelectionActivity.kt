package pdm.demos.mazepathfinder.config

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pdm.demos.mazepathfinder.domain.Search
import pdm.demos.mazepathfinder.ui.theme.MazePathFinderTheme

/**
 * Activity to select the search algorithm to use.
 */
class AlgorithmSelectionActivity : ComponentActivity() {

    companion object {
        const val SELECTED_RESULT_CODE = 1
        const val CANCELLED_RESULT_CODE = -1
        const val ALGORITHM_NAME = "algorithm"
    }

    private fun setSelectionResult(algorithm: Search) {
        setResult(SELECTED_RESULT_CODE, intent.putExtra(ALGORITHM_NAME, algorithm.name))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MazePathFinderTheme {
                AlgorithmSelectionScreen(
                    onSelected = { setSelectionResult(it) },
                    onCancelled = { setResult(CANCELLED_RESULT_CODE); finish() }
                )
            }
        }
    }
}
