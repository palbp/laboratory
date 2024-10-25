package pdm.demos.mazepathfinder.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pdm.demos.mazepathfinder.domain.Maze.Cell

/**
 * Represents a node in the graph used internally by the search algorithm. If there's a non unitary
 * cost for each step, the cost can be stored in the node.
 * @param cell the cell in the maze that this node represents
 * @param accumulatedCost the cost of the path from the start node to this node
 * @param parent the parent node in the graph, or null if this is the root node
 */
internal data class GraphNode(
    val cell: Cell,
    val parent: GraphNode? = null,
    val accumulatedCost: Int = 0
) {
    override fun toString() = "Node(cell=$cell, cost=$accumulatedCost)"
}

/**
 * Represents the open set, that is, the set of nodes that are candidates to be visited next by
 * the search algorithm.
 */
internal interface OpenSet {
    val contents: List<GraphNode>
    fun add(node: GraphNode)
    fun remove(): GraphNode?
    fun isEmpty(): Boolean
    fun isNotEmpty() = !isEmpty()
    fun addAll(nodes: Collection<GraphNode>)
}

/**
 * Finds a path through the given [maze], producing a flow of search states, thereby allowing the
 * caller to observe the search process. The flow ends when the search is completed, which is
 * signaled by a [SearchState.Found] state.
 *
 * Initial state: any cell in the maze can be the starting point. The maze must have exactly one
 * start cell.
 * Final state: any cell in the maze can be the goal. The maze must have exactly one goal cell.
 * Possible actions: move to any adjacent cell that is not a wall.
 * Cost: 1 for each step. The cost of the path is the number of steps, that is, the size of the
 * returned list.
 *
 * @param maze the maze to find the path in
 * @param openSet the open set to use in the search algorithm.
 * @return the flow of search states
 */
internal fun findPath(maze: Maze, openSet: OpenSet): Flow<SearchState> = flow {

    openSet.add(GraphNode(cell = maze.startCell, accumulatedCost = 1))
    val closedSet = mutableSetOf<GraphNode>()

    while (openSet.isNotEmpty()) {

        val current = openSet.remove() ?: break

        //print("current=$current | openSet = ${openSet.contents}")

        closedSet.add(current)
        emit(SearchState.Searching(visited = closedSet.map { it.cell }))

        if (current.cell == maze.goalCell) {
            emit(SearchState.Found(path = rebuildPath(current)))
            return@flow
        }

        openSet.addAll(
            maze.getNeighbors(current.cell)
                .filter { neighbor -> closedSet.none { it.cell == neighbor } }
                .map {
                    GraphNode(
                        cell = it,
                        parent = current,
                        accumulatedCost = current.accumulatedCost + 1
                    )
                }
        )

        //println(" | expanded=${openSet.contents}")
    }

    emit(SearchState.NotFound(visited = closedSet.map { it.cell }))
}

/**
 * Rebuilds the path from the given [goal] node to the start node, returning the list of cells that
 * form the path, starting from the start node.
 * @param goal the goal node
 * @return the list of cells that form the path
 */
internal fun rebuildPath(goal: GraphNode): List<Cell> {
    val path = mutableListOf(goal.cell)

    var current = goal
    while (current.parent != null) {
        current = current.parent
        path.add(current.cell)
    }

    return path.reversed()
}

/**
 * Represents the open set used on a breadth-first strategy.
 */
internal class BreadthFirstOpenSet(private val randomizeNeighbors: Boolean = false) : OpenSet {
    private val openSet = mutableListOf<GraphNode>()

    override val contents: List<GraphNode>
        get() = openSet.toList()

    override fun isEmpty() = openSet.isEmpty()
    override fun remove() = openSet.removeAt(0)

    override fun add(node: GraphNode) {
        openSet.add(node)
    }

    override fun addAll(nodes: Collection<GraphNode>) {
        openSet.addAll(if (randomizeNeighbors) nodes.shuffled() else nodes)
    }
}

/**
 * Represents the open set used on a depth-first strategy.
 * @param randomizeNeighbors whether to randomize the order of the neighbors when adding them to
 * the open set
 */
internal class DepthFirstOpenSet(private val randomizeNeighbors: Boolean = false) : OpenSet {
    private val openSet = mutableListOf<GraphNode>()

    override val contents: List<GraphNode>
        get() = openSet.toList()

    override fun isEmpty() = openSet.isEmpty()
    override fun remove() = openSet.removeAt(0)

    override fun add(node: GraphNode) {
        openSet.add(0, node)
    }

    override fun addAll(nodes: Collection<GraphNode>) {
        openSet.addAll(0, if (randomizeNeighbors) nodes.shuffled() else nodes)
    }
}

/**
 * Represents the open set used on a greedy strategy.
 * @param h the heuristic function to use. The heuristic function must return the estimated
 * cost from the given cell to the goal cell.
 */
internal class GreedySearchOpenSet(private val h: (GraphNode) -> Double) : OpenSet {
    private var openSet = listOf<GraphNode>()

    override val contents: List<GraphNode>
        get() = openSet.toList()

    override fun isEmpty() = openSet.isEmpty()
    override fun remove() = openSet.firstOrNull()?.also { openSet = openSet.drop(1) }

    override fun add(node: GraphNode) {
        openSet = (openSet + node).sortedBy { h(it) }
    }

    override fun addAll(nodes: Collection<GraphNode>) {
        openSet = (openSet + nodes).sortedBy { h(it) }
    }
}

/**
 * Represents the open set used on an A* strategy.
 * @param h the heuristic function to use. The heuristic function must return the estimated
 * cost from the given cell to the goal cell.
 */
internal class AStarOpenSet(private val h: (GraphNode) -> Double) : OpenSet {
    private var openSet = listOf<GraphNode>()

    override val contents: List<GraphNode>
        get() = openSet.toList()

    private fun GraphNode.g(): Double = accumulatedCost.toDouble()
    private fun GraphNode.f(): Double = g() + h(this)

    override fun isEmpty() = openSet.isEmpty()
    override fun remove() = openSet.firstOrNull()?.also { openSet = openSet.drop(1) }

    override fun add(node: GraphNode) {
        openSet = (openSet + node).sortedBy { it.f() }
    }

    override fun addAll(nodes: Collection<GraphNode>) {
        openSet = (openSet + nodes).sortedBy { it.f() }
    }
}