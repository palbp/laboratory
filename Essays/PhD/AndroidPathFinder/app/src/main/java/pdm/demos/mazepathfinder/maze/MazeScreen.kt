package pdm.demos.mazepathfinder.maze

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pdm.demos.mazepathfinder.maze.views.MazeScreenIdleView
import pdm.demos.mazepathfinder.maze.views.MazeScreenPathFoundView
import pdm.demos.mazepathfinder.maze.views.MazeScreenPathNotFoundView
import pdm.demos.mazepathfinder.maze.views.MazeScreenPausedSearchingPathView
import pdm.demos.mazepathfinder.maze.views.MazeScreenSearchingPathView
import pdm.demos.mazepathfinder.maze.views.mapResource
import pdm.demos.mazepathfinder.ui.theme.MazePathFinderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MazeScreen(viewModel: MazeScreenViewModel, onSelectAlgorithmIntent: () -> Unit) {
    MazePathFinderTheme {
        val state by viewModel.state.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(state.algorithm.mapResource())) },
                    actions = {
                        IconButton(
                            onClick = onSelectAlgorithmIntent,
                            enabled = state is MazeScreenState.Idle
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            when (val currentState = state) {
                is MazeScreenState.Idle ->
                    MazeScreenIdleView(
                        state = currentState,
                        onStartSearch = { viewModel.startSearch() },
                        modifier = Modifier.padding(paddingValues)
                    )

                is MazeScreenState.SearchingPath ->
                    MazeScreenSearchingPathView(
                        state = currentState,
                        onPauseSearch = { viewModel.pauseSearch() },
                        modifier = Modifier.padding(paddingValues),
                    )

                is MazeScreenState.PausedSearchingPath ->
                    MazeScreenPausedSearchingPathView(
                        state = currentState,
                        onResumeSearch = { viewModel.resumeSearch() },
                        modifier = Modifier.padding(paddingValues),
                    )

                is MazeScreenState.PathFound ->
                    MazeScreenPathFoundView(
                        state = currentState,
                        onResetMaze = { viewModel.resetMaze() },
                        modifier = Modifier.padding(paddingValues)
                    )

                is MazeScreenState.PathNotFound ->
                    MazeScreenPathNotFoundView(
                        state = currentState,
                        onResetMaze = { viewModel.resetMaze() },
                        modifier = Modifier.padding(paddingValues)
                    )


            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MazeScreenPreview() {
    MazeScreen(
        viewModel = MazeScreenViewModel(),
        onSelectAlgorithmIntent = {}
    )
}