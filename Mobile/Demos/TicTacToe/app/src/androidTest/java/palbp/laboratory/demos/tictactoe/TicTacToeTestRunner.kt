package palbp.laboratory.demos.tictactoe

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import palbp.laboratory.demos.tictactoe.lobby.Lobby
import palbp.laboratory.demos.tictactoe.lobby.LobbyPullStyle
import palbp.laboratory.demos.tictactoe.preferences.UserInfo
import palbp.laboratory.demos.tictactoe.preferences.UserInfoRepository

/**
 * The service locator to be used in the instrumented tests.
 */
class TicTacToeTestApplication : DependenciesContainer, Application() {

    override var userInfoRepo: UserInfoRepository =
        mockk(relaxed = true) {
            every { userInfo } returns UserInfo("nick", "moto")
        }

    override val lobbyPull: LobbyPullStyle
        get() = TODO("Remove this after next class")

    override val lobby: Lobby
        get() = mockk(relaxed = true) {
            coEvery { players } returns flow {
                listOf(
                    UserInfo("nick1", "moto1"),
                    UserInfo("nick2", "moto2")
                )
            }
        }
}

@Suppress("unused")
class TicTacToeTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TicTacToeTestApplication::class.java.name, context)
    }
}
