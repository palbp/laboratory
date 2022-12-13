package palbp.laboratory.demos.tictactoe.game.play.adapters

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import palbp.laboratory.demos.tictactoe.game.play.CleanupMatchesRule
import palbp.laboratory.demos.tictactoe.game.play.domain.Board
import palbp.laboratory.demos.tictactoe.game.play.domain.Coordinate
import palbp.laboratory.demos.tictactoe.game.play.domain.Game
import palbp.laboratory.demos.tictactoe.localTestPlayer
import palbp.laboratory.demos.tictactoe.testutils.SuspendingCountDownLatch
import palbp.laboratory.demos.tictactoe.testutils.SuspendingGate

@ExperimentalCoroutinesApi
class MatchFirebaseTests {

    @get:Rule
    val matchesRule = CleanupMatchesRule()

    @Test
    fun start_reports_new_game_on_returned_flow(): Unit = runTest {

        val sut = matchesRule.match
        val gameStateChangedGate = SuspendingGate()
        var game: Game? = null

        // Act
        val collectJob = launch {
            sut.start(
                localPlayer = localTestPlayer,
                challenge = matchesRule.remoteInitiatedChallenge
            ).collect {
                game = it
                gameStateChangedGate.open()
            }
        }

        gameStateChangedGate.await()
        collectJob.cancel()

        // Assert
        assertNotNull(game)
    }

    @Test
    fun start_publishes_match_when_local_player_is_challenged(): Unit = runTest {

        val sut = matchesRule.match
        val gameStateChangedGate = SuspendingGate()

        // Act
        val collectJob = launch {
            sut.start(
                localPlayer = localTestPlayer,
                challenge = matchesRule.remoteInitiatedChallenge
            ).collect {
                gameStateChangedGate.open()
            }
        }

        gameStateChangedGate.await()
        collectJob.cancel()

        // Assert
        val challenger = matchesRule.remoteInitiatedChallenge.challenger
        val matchDoc = matchesRule.app.emulatedFirestoreDb
            .collection(ONGOING)
            .document(challenger.id.toString())
            .get()
            .await()

        assertNotNull(matchDoc.toBoardOrNull())
    }

    @Test
    fun makeMove_publishes_new_game_state_on_flow(): Unit = runTest {

        val sut = matchesRule.match
        val expectedGameStatesCount = 2
        val gameStartedGate = SuspendingGate()
        val expectedGameStatesGate = SuspendingCountDownLatch(expectedGameStatesCount)
        val gameStates = mutableListOf<Game>()

        // Act
        val challenge = matchesRule.locallyInitiatedChallenge
        val collectJob = launch {
            sut.start(localPlayer = localTestPlayer, challenge = challenge)
                .collect {
                    gameStates.add(it)
                    expectedGameStatesGate.countDown()
                    if (gameStates.size == 1)
                        gameStartedGate.open()
                }
        }

        // simulate remote game start
        matchesRule.app.emulatedFirestoreDb
            .collection(ONGOING)
            .document(challenge.challenger.id.toString())
            .set(Board().toDocumentContent())
            .await()

        gameStartedGate.await()

        val at = Coordinate(1, 1)
        sut.makeMove(at)

        expectedGameStatesGate.await()
        collectJob.cancel()

        // Assert
        assertEquals(expectedGameStatesCount, gameStates.size)
        assertNotNull(gameStates[1].board[at])
    }

    @Test
    fun moves_made_by_remote_player_are_published_on_flow(): Unit = runTest {

        val sut = matchesRule.match
        val expectedGameStatesCount = 2
        val gameStartedGate = SuspendingGate()
        val expectedGameStatesGate = SuspendingCountDownLatch(expectedGameStatesCount)
        val gameStates = mutableListOf<Game>()

        // Act
        val collectJob = launch {
            sut.start(
                localPlayer = localTestPlayer,
                challenge = matchesRule.remoteInitiatedChallenge
            ).collect {
                gameStates.add(it)
                expectedGameStatesGate.countDown()
                if (gameStates.size == 1)
                    gameStartedGate.open()
            }
        }

        gameStartedGate.await()

        // Simulate move made by the remote player
        val challenger = matchesRule.remoteInitiatedChallenge.challenger
        val at = Coordinate(0, 0)
        matchesRule.app.emulatedFirestoreDb
            .collection(ONGOING)
            .document(challenger.id.toString())
            .set(Board().makeMove(at).toDocumentContent())
            .await()

        expectedGameStatesGate.await()
        collectJob.cancel()

        // Assert
        assertNotNull(gameStates[1].board[at])
    }

    @Test(expected = IllegalStateException::class)
    fun makeMove_when_not_the_local_player_turn_throws(): Unit = runTest {
        val sut = matchesRule.match
        val gameStateChangedGate = SuspendingGate()

        // Act
        val collectJob = launch {
            sut.start(
                localPlayer = localTestPlayer,
                challenge = matchesRule.remoteInitiatedChallenge
            ).collect {
                gameStateChangedGate.open()
            }
        }

        gameStateChangedGate.await()
        collectJob.cancel()

        sut.makeMove(Coordinate(0, 0))
    }
}
