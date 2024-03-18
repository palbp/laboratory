package palbp.laboratory.simplexludum.ui.mycollection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import kotlin.test.assertIs

class MyCollectionScreenModelTests {

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initially_the_state_is_idle() = runTest(testDispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGamesList = ::getFakeGameLists,
            getLatestGames = ::getFakeLatestGames
        )
        // Act

        // Assert
        assertIs<ScreenState.Idle>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loading() = runTest(testDispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGamesList = ::getFakeGameLists,
            getLatestGames = ::getFakeLatestGames,
        )

        // Act
        model.fetchScreenData()

        // Assert
        assertIs<ScreenState.Loading>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loaded_once_is_obtained() = runTest(testDispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGamesList = ::getFakeGameLists,
            getLatestGames = ::getFakeLatestGames,
        )

        // Act
        model.fetchScreenData()
        testScheduler.advanceUntilIdle()

        // Assert
        assertIs<ScreenState.Loaded>(model.state)
    }
}

private fun getFakeGameLists(): List<GameListSummary> = listOf(
    GameListSummary("Platinum", 19),
    GameListSummary("Completed", 36),
    GameListSummary("Backlog", 23),
    GameListSummary("Wishlist", 10),
    GameListSummary("Collections", 3)
)

private fun getFakeLatestGames(): List<Game> = listOf(
    Game(
        name = "name1",
        developer = "developer1",
        genres = listOf(Genre.ADVENTURE),
        platform = Platform.PS4,
        distribution = Distribution.PHYSICAL
    ),
    Game(
        name = "name2",
        developer = "developer1",
        genres = listOf(Genre.ACTION),
        platform = Platform.PS5,
        distribution = Distribution.SUBSCRIPTION
    )
)
