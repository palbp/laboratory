package pdm.demos.mazepathfinder.domain

import kotlinx.coroutines.flow.Flow
import pdm.demos.mazepathfinder.domain.Maze.Cell
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * The state of the search algorithm.
 */
sealed interface SearchState {
    data class Searching(val visited: List<Cell>) : SearchState
    data class Found(val path: List<Cell>) : SearchState
    data class NotFound(val visited: List<Cell>) : SearchState
}

/**
 * The supported search algorithms.
 */
enum class Search {
    BreadthFirst,
    BreadthFirstRandomized,
    DepthFirst,
    DepthFirstRandomized,
    GreedySearchManhattan,
    GreedySearchEuclidean,
    AStarManhattan,
    AStarEuclidean
}

/**
 * Finds a path through the given [maze] using the specified search algorithm, producing a flow
 * of search states, thereby allowing the caller to observe the search process. The flow ends
 * when the search is completed, which is signaled by a [SearchState.Found] state.
 */
fun Search.findPath(maze: Maze): Flow<SearchState> = when (this) {
    Search.BreadthFirst -> findPath(maze, BreadthFirstOpenSet())
    Search.BreadthFirstRandomized -> findPath(maze, BreadthFirstOpenSet(randomizeNeighbors = true))
    Search.DepthFirst -> findPath(maze, DepthFirstOpenSet())
    Search.DepthFirstRandomized -> findPath(maze, DepthFirstOpenSet(randomizeNeighbors = true))
    Search.GreedySearchManhattan -> findPath(
        maze,
        GreedySearchOpenSet(h = { it.cell.manhattanDistanceTo(maze.goalCell) })
    )

    Search.GreedySearchEuclidean -> findPath(
        maze,
        GreedySearchOpenSet(h = { it.cell.euclideanDistanceTo(maze.goalCell) })
    )

    Search.AStarManhattan -> findPath(
        maze,
        AStarOpenSet(h = { it.cell.manhattanDistanceTo(maze.goalCell) })
    )

    Search.AStarEuclidean -> findPath(
        maze,
        AStarOpenSet(h = { it.cell.euclideanDistanceTo(maze.goalCell) })
    )
}

fun Cell.euclideanDistanceTo(other: Cell): Double {
    val dx = column - other.column
    val dy = row - other.row
    return sqrt((dx * dx + dy * dy).toDouble())
}

fun Cell.manhattanDistanceTo(other: Cell): Double {
    val dx = column - other.column
    val dy = row - other.row
    return (abs(dx) + abs(dy)).toDouble()
}