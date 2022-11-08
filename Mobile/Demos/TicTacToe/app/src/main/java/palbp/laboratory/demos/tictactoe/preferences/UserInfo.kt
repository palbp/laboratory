package palbp.laboratory.demos.tictactoe.preferences

/**
 * Represents the user information.
 *
 * @property [nick] the user's nick name
 * @property [moto] the user's moto, if he has one
 */
data class UserInfo(val nick: String, val moto: String? = null) {
    init {
        require(nick.isNotBlank())
        if (moto != null)
            require(moto.isNotBlank())
    }
}