package palbp.laboratory.demos.tictactoe.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.demos.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun LobbyScreen() {
    TicTacToeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("LobbyScreen"),
        ) {
            // TODO
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    LobbyScreen()
}