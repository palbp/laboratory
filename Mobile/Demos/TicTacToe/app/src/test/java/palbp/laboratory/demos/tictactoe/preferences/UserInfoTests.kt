package palbp.laboratory.demos.tictactoe.preferences

import org.junit.Test

class UserInfoTests {
    @Test(expected = IllegalArgumentException::class)
    fun `create instance with blank nick throws`() {
        UserInfo(nick = "\n  \t ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create instance with blank moto throws`() {
        UserInfo(nick = "nick", moto = "\n  \t ")
    }

    @Test
    fun `create instance with non empty nick and moto succeeds`() {
        UserInfo(nick = "nick", moto = "moto")
    }

    @Test
    fun `create instance with non empty nick and no moto`() {
        UserInfo(nick = "nick")
    }
}