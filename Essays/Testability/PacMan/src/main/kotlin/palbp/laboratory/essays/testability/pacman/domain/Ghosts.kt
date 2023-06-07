package palbp.laboratory.essays.testability.pacman.domain

/**
 * Initial properties of the ghosts.
 */
val ghostsStartingPosition = Coordinate(row = 11, column = 13)
val ghostsStartingFacing = Direction.LEFT

/**
 * Represents a ghost in the game arena.
 * @property id the ghost's identity
 * @property at the current coordinate of the ghost
 * @property facing the direction the ghost is facing, and therefore the direction in which it will move next.
 * @property previouslyAt the ghost's previous location in the maze. This is used to keep track of the ghost's movement.
 * @property mode the ghost's current mode.
 * @property movement the criteria that determines how the ghost moves. Movement criteria are functions that receive
 * the arena, the ghost and a boolean indicating whether the ghost has just entered scatter mode. The function returns
 * the new ghost instance.
 */
data class Ghost(
    val id: GhostId,
    val at: Coordinate,
    val facing: Direction,
    val previouslyAt: Coordinate? = null,
    val mode: GhostMode = GhostMode.CHASE,
    val movement: (Arena, Ghost, Boolean) -> Ghost = ::changeToRandomDirectionWhenFacingWall
)

/**
 * Represents the ghost's mode. Each ghost has a different behaviour depending on its mode.
 */
enum class GhostMode { CHASE, SCATTER }

/**
 * Each ghost has its own identity. The order of the values in this enum is important, as it's used to determine
 * the ghost's color.
 */
enum class GhostId { SHADOW, SPEEDY, BASHFUL, POKEY }

/**
 * Moves the ghost to the next cell in the direction it's facing. If there's a wall in the way, the ghost changes
 * direction.
 */
fun Ghost.move(arena: Arena, enteredScatterMode: Boolean): Ghost = this.movement(arena, this, enteredScatterMode)

/**
 * Changes the ghost's mode to scatter mode.
 */
fun Ghost.enterScatterMode(): Ghost = copy(mode = GhostMode.SCATTER, movement = ::runAwayFromHero)

/**
 * Changes the ghost's mode to chase mode.
 */
fun Ghost.enterChaseMode(): Ghost = copy(mode = GhostMode.CHASE, movement = ::changeToRandomDirectionWhenFacingWall)

/**
 * Movement criteria that moves the ghost in the direction he is facing until a wall is found. When that happens,
 * the ghost changes direction to a random one that doesn't have a wall in the way.
 */
fun changeToRandomDirectionWhenFacingWall(arena: Arena, ghost: Ghost, enteredScatterMode: Boolean): Ghost {
    val nextFacing = getCandidateDirections(arena, ghost, enteredScatterMode).random()
    return ghost.copy(at = ghost.at + nextFacing, facing = nextFacing, previouslyAt = ghost.at)
}

/**
 * Movement criteria that moves the ghost away from the hero.
 */
fun runAwayFromHero(arena: Arena, ghost: Ghost, enteredScatterMode: Boolean): Ghost {
    val nextFacing = getCandidateDirections(arena, ghost, enteredScatterMode)
        .maxBy { (ghost.at + it).distanceTo(arena.pacMan.at) }
    return ghost.copy(at = ghost.at + nextFacing, facing = nextFacing, previouslyAt = ghost.at)
}

private fun getCandidateDirections(arena: Arena, ghost: Ghost, enteredScatterMode: Boolean): List<Direction> =
    Direction.values().filter {
        !arena.maze.hasWall(ghost.at + it) &&
            if (enteredScatterMode) it == ghost.facing.opposite() else it != ghost.facing.opposite()
    }
