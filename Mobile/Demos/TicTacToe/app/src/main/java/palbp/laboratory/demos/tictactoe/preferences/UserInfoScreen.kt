package palbp.laboratory.demos.tictactoe.preferences

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import palbp.laboratory.demos.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun PreferencesScreen() {
    TicTacToeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("PreferencesScreen"),
        ) {
            // TODO
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PreferencesScreen()
}