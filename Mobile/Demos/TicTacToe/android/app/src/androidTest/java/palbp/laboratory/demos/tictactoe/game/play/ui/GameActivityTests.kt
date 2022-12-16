package palbp.laboratory.demos.tictactoe.game.play.ui

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import palbp.laboratory.demos.tictactoe.R
import palbp.laboratory.demos.tictactoe.game.lobby.domain.Challenge
import palbp.laboratory.demos.tictactoe.game.lobby.domain.PlayerInfo
import palbp.laboratory.demos.tictactoe.game.lobby.domain.firstToMove
import palbp.laboratory.demos.tictactoe.game.play.domain.*
import palbp.laboratory.demos.tictactoe.localTestPlayer
import palbp.laboratory.demos.tictactoe.otherTestPlayersInLobby
import palbp.laboratory.demos.tictactoe.testutils.PreserveDefaultDependencies
import palbp.laboratory.demos.tictactoe.testutils.createPreserveDefaultDependenciesComposeRule

private const val STARTUP_DELAY = 2000L

@RunWith(AndroidJUnit4::class)
class GameActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRule()

    private val delayedMockMatch: Match = mockk(relaxed = true) {
        val localPlayer = slot<PlayerInfo>()
        val challenge = slot<Challenge>()
        coEvery { start(capture(localPlayer), capture(challenge)) } answers {
            flow {
                delay(STARTUP_DELAY)
                val localMarker = getLocalPlayerMarker(localPlayer.captured, challenge.captured)
                emit(GameStarted(Game(localMarker, Board())))
            }
        }
    }

    private val immediateMockMatch: Match = mockk(relaxed = true) {
        val localPlayer = slot<PlayerInfo>()
        val challenge = slot<Challenge>()
        coEvery { start(capture(localPlayer), capture(challenge)) } answers {
            flow {
                val localMarker = getLocalPlayerMarker(localPlayer.captured, challenge.captured)
                emit(GameStarted(Game(localMarker, Board())))
            }
        }

        coEvery { makeMove(any()) } returns Unit
    }


    private val application by lazy {
        (testRule.activityRule as PreserveDefaultDependencies).testApplication
    }

    @Test
    fun game_activity_starts_by_displaying_starting_dialog() {

        // Arrange
        application.match = delayedMockMatch
        val intent = Intent(application, GameActivity::class.java).also {
            val challenge = Challenge(
                challenger = otherTestPlayersInLobby.first(),
                challenged = localTestPlayer
            )
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            // Assert
            testRule.onNodeWithTag(StartingMatchDialogTag).assertExists()
            testRule
                .onNodeWithTag(GameScreenTitleTag)
                .assertTextEquals(application.getString(R.string.game_screen_waiting))
        }
    }

    @Test
    fun when_game_starts_the_player_turn_is_displayed() {

        // Arrange
        val challenge = Challenge(
            challenger = otherTestPlayersInLobby.first(),
            challenged = localTestPlayer
        )
        val intent = Intent(application, GameActivity::class.java).also {
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            // Assert
            val title =
                if (localTestPlayer == challenge.firstToMove) R.string.game_screen_your_turn
                else R.string.game_screen_opponent_turn

            testRule.onNodeWithTag(StartingMatchDialogTag).assertDoesNotExist()
            testRule
                .onNodeWithTag(GameScreenTitleTag)
                .assertTextEquals(application.getString(title))
        }
    }

    @Test
    fun when_its_local_player_turn_board_is_enabled() {

        // Arrange
        val challenge = Challenge(
            challenger = localTestPlayer,
            challenged = otherTestPlayersInLobby.first()
        )
        val intent = Intent(application, GameActivity::class.java).also {
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            // Assert
            assertEquals(challenge.firstToMove, localTestPlayer)
            testRule
                .onAllNodesWithTag(TileViewTag)
                .assertAll(isEnabled())
        }
    }

    @Test
    fun when_its_local_player_turn_board_clicks_make_moves() {

        // Arrange
        application.match = immediateMockMatch
        val challenge = Challenge(
            challenger = localTestPlayer,
            challenged = otherTestPlayersInLobby.first()
        )
        val intent = Intent(application, GameActivity::class.java).also {
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            testRule
                .onAllNodesWithTag(TileViewTag)
                .onFirst()
                .performClick()

            testRule.waitForIdle()

            // Assert
            assertEquals(challenge.firstToMove, localTestPlayer)
            coVerify(exactly = 1) { immediateMockMatch.makeMove(any()) }
        }
    }

    @Test
    fun when_its_remote_player_turn_board_is_disabled() {

        // Arrange
        val challenge = Challenge(
            challenger = otherTestPlayersInLobby.first(),
            challenged = localTestPlayer
        )
        val intent = Intent(application, GameActivity::class.java).also {
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            // Assert
            assertNotEquals(challenge.firstToMove, localTestPlayer)
            testRule
                .onAllNodesWithTag(TileViewTag)
                .assertAll(isNotEnabled())
        }
    }

    @Test
    fun when_its_remote_player_turn_board_clicks_do_not_make_moves() {

        // Arrange
        application.match = immediateMockMatch
        val challenge = Challenge(
            challenger = otherTestPlayersInLobby.first(),
            challenged = localTestPlayer
        )
        val intent = Intent(application, GameActivity::class.java).also {
            it.putExtra(GameActivity.MATCH_INFO_EXTRA, MatchInfo(localTestPlayer, challenge))
        }

        // Act
        ActivityScenario.launch<GameActivity>(intent).use {

            testRule
                .onAllNodesWithTag(TileViewTag)
                .onFirst()
                .performClick()

            testRule.waitForIdle()

            // Assert
            assertNotEquals(challenge.firstToMove, localTestPlayer)
            coVerify(exactly = 0) { immediateMockMatch.makeMove(any()) }
        }
    }
}