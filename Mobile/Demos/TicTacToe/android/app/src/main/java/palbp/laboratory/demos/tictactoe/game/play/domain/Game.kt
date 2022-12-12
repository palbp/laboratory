package palbp.laboratory.demos.tictactoe.game.play.domain

import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo

/**
 * Represents a Tic-Tac-Toe game. Instances are immutable.
 * @property localPlayerMarker  The local player marker
 * @property board              The game board
 */
data class Game(
    val localPlayerMarker: Marker = Marker.firstToMove,
    val board: Board = Board()
)

/**
 * Makes a move on this [Game], returning a new instance.
 * @param at the coordinates where the move is to be made
 * @return the new [Game] instance
 * @throws IllegalStateException if its an invalid move, either because its
 * not the local player's turn or the move cannot be made on that location
 */
fun Game.makeMove(at: Coordinate): Game {
    check(localPlayerMarker == board.turn)
    return copy(board = board.makeMove(at))
}

/**
 * Gets which marker is to be assigned to the local player for the given challenge.
 */
fun getLocalPlayerMarker(localPlayer: PlayerInfo, challenge: Challenge) =
    if (localPlayer == challenge.challenged) Marker.firstToMove.other
    else Marker.firstToMove