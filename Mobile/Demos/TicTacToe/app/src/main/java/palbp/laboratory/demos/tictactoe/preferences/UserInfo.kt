package palbp.laboratory.demos.tictactoe.preferences

data class UserInfo(val nick: String, val moto: String? = null) {
    init {
        require(nick.isNotBlank())
        if (moto != null)
            require(moto.isNotBlank())
    }
}