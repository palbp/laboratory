package palbp.laboratory.codelabs.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import palbp.laboratory.codelabs.composebasics.ui.theme.ComposeBasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsCodelabTheme {
                MyApp(listOf("World", "Compose"))
            }
        }
    }
}

@Composable
fun MyApp(names: List<String>) {
    // A surface container using the 'background' color from the theme
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Column {
            names.forEach {
                Greeting(it)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello,")
                Text(text = "$name!")
            }
            
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Show more")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsCodelabTheme {
        MyApp(listOf("World", "Compose"))
    }
}