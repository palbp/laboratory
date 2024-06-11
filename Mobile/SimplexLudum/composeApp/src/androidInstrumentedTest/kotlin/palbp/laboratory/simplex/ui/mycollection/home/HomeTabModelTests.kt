package palbp.laboratory.simplex.ui.mycollection.home

import kotlinx.coroutines.test.runTest
import org.junit.Rule
import palbp.laboratory.simplex.infrastructure.fakeGetGameLists
import palbp.laboratory.simplex.infrastructure.fakeGetLatestGames
import palbp.laboratory.simplex.ui.MainDispatcherTestRule
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class HomeTabModelTests {

    @get:Rule
    private val testRule = MainDispatcherTestRule()

    @Test
    fun initially_the_state_is_idle() = runTest(testRule.dispatcher) {
        // Arrange
        val model = HomeTabViewModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames
        )
        // Act

        // Assert
        assertIs<HomeTabScreenState.Idle>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loading() = runTest(testRule.dispatcher) {
        // Arrange
        val model = HomeTabViewModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        val job = model.fetchData()

        // Assert
        assertNotNull(job)
        assertIs<HomeTabScreenState.Loading>(model.state)
    }

    @Test
    fun fetch_screen_data_transitions_to_loaded_once_it_is_obtained() = runTest(testRule.dispatcher) {
        // Arrange
        val model = HomeTabViewModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        model.fetchData()?.join()

        // Assert
        assertIs<HomeTabScreenState.Loaded>(model.state)
    }

    @Test
    fun fetch_screen_data_only_fetches_when_not_loading() = runTest(testRule.dispatcher) {
        // Arrange
        val model = HomeTabViewModel(
            getGameLists = ::fakeGetGameLists,
            getLatestGames = ::fakeGetLatestGames,
        )

        // Act
        model.fetchData()
        val job = model.fetchData()

        // Assert
        assertNull(job)
        assertIs<HomeTabScreenState.Loading>(model.state)
    }
}