package palbp.laboratory.demos.tictactoe.game

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.tictactoe.DependenciesContainer
import palbp.laboratory.demos.tictactoe.game.lobby.LobbyScreenViewModel
import palbp.laboratory.demos.tictactoe.preferences.UserInfo

@RunWith(AndroidJUnit4::class)
class LobbyScreenViewModelTests {

    private val app by lazy {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as DependenciesContainer
    }

    private val localUser = UserInfo("test", "moto")
    private val sut = LobbyScreenViewModel(app.lobby, app.userInfoRepo)

    @Test
    fun lobby_isEmpty_until_entered() {
        fail()
    }
}
