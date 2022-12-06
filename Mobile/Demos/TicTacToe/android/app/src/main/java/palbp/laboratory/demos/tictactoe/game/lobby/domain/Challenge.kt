package palbp.laboratory.demos.tictactoe.game.lobby.domain

/**
 * Data type that characterizes challenges.
 * @property challenger     The challenger information
 * @property challenged     The information of the challenged player
 */
data class Challenge(val challenger: PlayerInfo, val challenged: PlayerInfo)