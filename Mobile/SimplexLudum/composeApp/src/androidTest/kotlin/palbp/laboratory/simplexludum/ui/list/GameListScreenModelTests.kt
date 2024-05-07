package palbp.laboratory.simplexludum.ui.list

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.MainDispatcherTestRule
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GameListScreenModelTests {

    private val testRule = MainDispatcherTestRule()

    @Test
    fun initially_the_state_is_idle() = runTest(testRule.dispatcher) {
        // Arrange
        val model = GameListScreenModel(getGameList = { fakeGetGames() })
        // Act

        // Assert
        assertIs<GameListScreenState.Idle>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loading() = runTest(testRule.dispatcher) {
        // Arrange
        val model = GameListScreenModel(getGameList = { fakeGetGames() })

        // Act
        val job = model.fetchFilteredGameList(query = "")

        // Assert
        assertNotNull(job)
        assertIs<GameListScreenState.Loading>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loaded_once_it_is_obtained() = runTest(testRule.dispatcher) {
        // Arrange
        val model = GameListScreenModel(getGameList = { fakeGetGames() })

        // Act
        model.fetchFilteredGameList(query = "").join()

        // Assert
        assertIs<GameListScreenState.Loaded>(model.state)
    }

    @Test
    fun fetch_screen_data_cancels_ongoing_job() = runTest(testRule.dispatcher) {
        // Arrange
        val model = GameListScreenModel(getGameList = { fakeGetGames() })

        // Act
        val firstJob = model.fetchFilteredGameList(query = "")
        model.fetchFilteredGameList(query = "").join()

        // Assert
        assertTrue(firstJob.isCancelled)
        assertIs<GameListScreenState.Loaded>(model.state)
    }
}

private suspend fun fakeGetGames(): List<Game> {

    delay(1000)

    return listOf(
        Game(
            name = "name1",
            developer = "developer1",
            genres = setOf(Genre.ADVENTURE),
            platform = Platform.PS4,
            distribution = Distribution.PHYSICAL
        ),
        Game(
            name = "name2",
            developer = "developer1",
            genres = setOf(Genre.ACTION),
            platform = Platform.PS5,
            distribution = Distribution.SUBSCRIPTION
        )
    )
}