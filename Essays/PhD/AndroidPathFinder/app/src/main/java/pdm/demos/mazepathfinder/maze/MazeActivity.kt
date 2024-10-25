package pdm.demos.mazepathfinder.maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import pdm.demos.mazepathfinder.config.AlgorithmSelectionActivity
import pdm.demos.mazepathfinder.domain.Search

/**
 * Main activity for the MazePathFinder app.
 */
class MazeActivity : ComponentActivity() {

    private val viewModel by viewModels<MazeScreenViewModel>()

    private val algorithmSelectionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == AlgorithmSelectionActivity.SELECTED_RESULT_CODE) {
                activityResult.data?.getStringExtra(AlgorithmSelectionActivity.ALGORITHM_NAME)
                    ?.let {
                        viewModel.selectAlgorithm(Search.valueOf(it))
                    }
            }
        }

    private val algorithmSelectionIntent by lazy {
        Intent(
            this,
            AlgorithmSelectionActivity::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MazeScreen(
                viewModel = viewModel,
                onSelectAlgorithmIntent = {
                    algorithmSelectionLauncher.launch(algorithmSelectionIntent)
                }
            )
        }
    }
}
