package palbp.laboratory.simplexludum.ui.mycollection

import kotlinx.coroutines.test.runTest
import org.junit.Test
import palbp.laboratory.simplexludum.domain.Distribution
import palbp.laboratory.simplexludum.domain.Game
import palbp.laboratory.simplexludum.domain.GameListSummary
import palbp.laboratory.simplexludum.domain.Genre
import palbp.laboratory.simplexludum.domain.Platform
import palbp.laboratory.simplexludum.ui.MainDispatcherTestRule
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MyCollectionScreenModelTests {

    private val testRule = MainDispatcherTestRule()

    @Test
    fun initially_the_state_is_idle() = runTest(testRule.dispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames
        )
        // Act

        // Assert
        assertIs<ScreenState.Idle>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loading() = runTest(testRule.dispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        val job = model.fetchScreenData()

        // Assert
        assertNotNull(job)
        assertIs<ScreenState.Loading>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loaded_once_is_obtained() = runTest(testRule.dispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        model.fetchScreenData()?.join()

        // Assert
        assertIs<ScreenState.Loaded>(model.state)
    }

    @Test
    fun fetch_screen_data_only_fetches_when_not_loading() = runTest(testRule.dispatcher) {
        // Arrange
        val model = MyCollectionScreenModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        model.fetchScreenData()
        val job = model.fetchScreenData()

        // Assert
        assertNull(job)
        assertIs<ScreenState.Loading>(model.state)
    }
}

private fun fakeGetGameLists(): List<GameListSummary> = listOf(
    GameListSummary("Platinum", 19),
    GameListSummary("Completed", 36),
    GameListSummary("Backlog", 23),
    GameListSummary("Wishlist", 10),
    GameListSummary("Collections", 3)
)

private fun fakeGetLatestGames(): List<Game> = listOf(
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
