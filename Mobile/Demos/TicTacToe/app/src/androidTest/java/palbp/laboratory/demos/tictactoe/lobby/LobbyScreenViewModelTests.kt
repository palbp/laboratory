package palbp.laboratory.demos.tictactoe.lobby

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.tictactoe.DependenciesContainer

@RunWith(AndroidJUnit4::class)
class LobbyScreenViewModelTests {

    private val lobby by lazy {
        (InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as DependenciesContainer).lobby
    }

    private val sut = LobbyScreenViewModel(lobby)

    @Test
    fun lobby_isEmpty_until_entered() {
        fail()
    }
}
