package pdm.demos.mazepathfinder.maze.views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.demos.mazepathfinder.R
import pdm.demos.mazepathfinder.domain.Maze.Cell
import pdm.demos.mazepathfinder.domain.Search
import pdm.demos.mazepathfinder.domain.buildTheSampleMaze
import pdm.demos.mazepathfinder.maze.MazeScreenState
import pdm.demos.mazepathfinder.ui.theme.MazePathFinderTheme

@StringRes
fun Search.mapResource(): Int = when (this) {
    Search.BreadthFirst -> R.string.search_algorithm_breadth_first
    Search.DepthFirst -> R.string.search_algorithm_depth_first
    Search.BreadthFirstRandomized -> R.string.search_algorithm_breadth_first_randomized
    Search.DepthFirstRandomized -> R.string.search_algorithm_depth_first_randomized
    Search.GreedySearchManhattan -> R.string.search_algorithm_greedy_search_manhattan
    Search.GreedySearchEuclidean -> R.string.search_algorithm_greedy_search_euclidean
    Search.AStarManhattan -> R.string.search_algorithm_a_star_manhattan
    Search.AStarEuclidean -> R.string.search_algorithm_a_star_euclidean
}

@Composable
fun MazeScreenIdleView(
    state: MazeScreenState.Idle,
    onStartSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        MazeView(
            maze = state.maze,
            highlighted = emptyList(),
            modifier = Modifier
                .padding(32.dp)
                .weight(1.0f, true)
                .fillMaxWidth(),
        )
        Button(onClick = onStartSearch) {
            Text(
                text = stringResource(id = R.string.button_start_text),
                modifier = Modifier.padding(all = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MazeScreenInitialViewPreview() {
    MazePathFinderTheme {
        MazeScreenIdleView(
            state = MazeScreenState.Idle(
                maze = buildTheSampleMaze(),
                algorithm = Search.BreadthFirst
            ),
            onStartSearch = {}
        )
    }
}

@Composable
fun MazeScreenSearchingPathView(
    state: MazeScreenState.SearchingPath,
    onPauseSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        MazeView(
            maze = state.maze,
            highlighted = state.visited,
            modifier = Modifier
                .padding(32.dp)
                .weight(1.0f, true)
                .fillMaxWidth(),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {}, enabled = false) {
                Text(
                    text = stringResource(id = R.string.button_searching_text),
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
            Button(onClick = onPauseSearch) {
                Text(
                    text = stringResource(id = R.string.button_pause_searching_text),
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MazeScreenSearchingPathViewPreview() {
    MazePathFinderTheme {
        MazeScreenSearchingPathView(
            state = MazeScreenState.SearchingPath(
                maze = buildTheSampleMaze(),
                visited = listOf(Cell(4, 4), Cell(4, 3)),
                algorithm = Search.BreadthFirst
            ),
            onPauseSearch = {}
        )
    }
}

@Composable
fun MazeScreenPausedSearchingPathView(
    state: MazeScreenState.PausedSearchingPath,
    onResumeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        MazeView(
            maze = state.maze,
            highlighted = state.visited,
            modifier = Modifier
                .padding(32.dp)
                .weight(1.0f, true)
                .fillMaxWidth(),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {}, enabled = false) {
                Text(
                    text = stringResource(id = R.string.button_searching_text),
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
            Button(onClick = onResumeSearch) {
                Text(
                    text = stringResource(id = R.string.button_resume_searching_text),
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
fun MazeScreenPathFoundView(
    state: MazeScreenState.PathFound,
    onResetMaze: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        MazeView(
            maze = state.maze,
            highlighted = state.path,
            modifier = Modifier
                .padding(32.dp)
                .weight(1.0f, true)
                .fillMaxWidth(),
        )
        Button(onClick = onResetMaze) {
            Text(
                text = stringResource(id = R.string.button_reset_text),
                modifier = Modifier.padding(all = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable

fun MazeScreenPathNotFoundView(
    state: MazeScreenState.PathNotFound,
    onResetMaze: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        MazeView(
            maze = state.maze,
            highlighted = emptyList(),
            modifier = Modifier
                .padding(32.dp)
                .weight(1.0f, true)
                .fillMaxWidth(),
        )
        Button(onClick = onResetMaze) {
            Text(
                text = stringResource(id = R.string.button_reset_text),
                modifier = Modifier.padding(all = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MazeScreenPathFoundViewPreview() {
    MazePathFinderTheme {
        MazeScreenPathFoundView(
            state = MazeScreenState.PathFound(
                maze = buildTheSampleMaze(),
                path = listOf(
                    Cell(4, 4),
                    Cell(3, 4),
                    Cell(3, 3),
                    Cell(3, 2),
                    Cell(2, 2),
                    Cell(2, 3),
                    Cell(1, 3),
                    Cell(1, 2),
                    Cell(0, 2),
                    Cell(0, 3),
                    Cell(0, 4)
                ),
                algorithm = Search.BreadthFirst
            ),
            onResetMaze = {}
        )
    }
}