package palbp.laboratory.codelabs.composebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import palbp.laboratory.codelabs.composebasics.ui.theme.ComposeBasicsCodelabTheme

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    // A surface container using the 'background' color from the theme
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        LazyColumn {
            items(names) {
                Greeting(it)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
                )
            }

            OutlinedButton(onClick = { expanded = !expanded }) {
                if (expanded) Text(text = "Show less") else Text(text = "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun GreetingsScreenPreview() {
    ComposeBasicsCodelabTheme {
        Greetings()
    }
}