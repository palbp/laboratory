package palbp.laboratory.demos.tictactoe.game.play.domain

/**
 * Represents a Tic-Tac-Toe game. Instances are immutable.
 * @property localPlayerMarker  The local player marker
 * @property board              The game board
 */
data class Game(val localPlayerMarker: Marker, val board: Board)
