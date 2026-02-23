package pdm.demos.mazepathfinder.maze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pdm.demos.mazepathfinder.domain.Maze
import pdm.demos.mazepathfinder.domain.Maze.Cell
import pdm.demos.mazepathfinder.domain.Search
import pdm.demos.mazepathfinder.domain.SearchState
import pdm.demos.mazepathfinder.domain.buildTheSampleMaze
import pdm.demos.mazepathfinder.domain.findPath
import pdm.demos.mazepathfinder.utils.SuspendingGate

/**
 * Represents the set of possible states for the Maze screen.
 */
sealed interface MazeScreenState {
    val maze: Maze
    val algorithm: Search

    data class Idle(override val maze: Maze, override val algorithm: Search) : MazeScreenState

    data class SearchingPath(
        override val maze: Maze,
        override val algorithm: Search,
        val visited: List<Cell>
    ) : MazeScreenState

    data class PausedSearchingPath(
        override val maze: Maze,
        override val algorithm: Search,
        val visited: List<Cell>
    ) : MazeScreenState

    data class PathFound(
        override val maze: Maze,
        override val algorithm: Search,
        val path: List<Cell>
    ) : MazeScreenState

    data class PathNotFound(
        override val maze: Maze,
        override val algorithm: Search,
        val visited: List<Cell>
    ) : MazeScreenState
}

/**
 * ViewModel for the Maze screen.
 */
class MazeScreenViewModel : ViewModel() {

    private val _state =
        MutableStateFlow<MazeScreenState>(
            MazeScreenState.Idle(
                maze = buildTheSampleMaze(),
                algorithm = Search.BreadthFirst
            )
        )
    val state: StateFlow<MazeScreenState> = _state.asStateFlow()

    private val gate = SuspendingGate()

    /**
     * Starts the search for a path in the maze, if it is not already running.
     */
    fun startSearch() {
        _state.value.let { observedState ->
            if (observedState is MazeScreenState.Idle) {
                viewModelScope.launch {
                    val pathFinderState =
                        observedState.algorithm.findPath(maze = observedState.maze)
                    pathFinderState
                        .map { step ->
                            when (step) {
                                is SearchState.Searching -> MazeScreenState.SearchingPath(
                                    maze = observedState.maze,
                                    visited = step.visited,
                                    algorithm = observedState.algorithm
                                )

                                is SearchState.Found ->
                                    MazeScreenState.PathFound(
                                        maze = observedState.maze,
                                        path = step.path,
                                        algorithm = observedState.algorithm
                                    )

                                is SearchState.NotFound -> MazeScreenState.PathNotFound(
                                    maze = observedState.maze,
                                    algorithm = observedState.algorithm,
                                    visited = step.visited
                                )
                            }
                        }
                        .flowOn(Dispatchers.Default)
                        .collect {
                            delay(700)
                            gate.await()
                            _state.value = it
                        }
                }
            }
        }
    }

    /**
     * Pauses the search algorithm
     */
    fun pauseSearch() {
        _state.value.let {
            if (it is MazeScreenState.SearchingPath) {
                viewModelScope.launch {
                    gate.close()
                    _state.value =
                        MazeScreenState.PausedSearchingPath(
                            maze = it.maze,
                            visited = it.visited,
                            algorithm = it.algorithm
                        )
                }
            }
        }
    }

    /**
     * Resumes the search algorithm
     */
    fun resumeSearch() {
        _state.value.let { observedState ->
            if (observedState is MazeScreenState.PausedSearchingPath) {
                viewModelScope.launch {
                    gate.open()
                }
            }
        }
    }

    /**
     * Selects the search algorithm to use.
     */
    fun selectAlgorithm(algorithm: Search) {
        _state.value.let {
            if (it is MazeScreenState.Idle) {
                _state.value = it.copy(algorithm = algorithm)
            }
        }
    }

    /**
     * Resets the maze to its initial state.
     */
    fun resetMaze() {
        _state.value.let {
            if (it is MazeScreenState.PathFound || it is MazeScreenState.PathNotFound)
                _state.value = MazeScreenState.Idle(
                    maze = it.maze,
                    algorithm = it.algorithm
                )
        }
    }
}