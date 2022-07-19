package palbp.laboratory.codelabs.basicstate

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Log.v("BasicStateAppTag", "WaterCounter composed")
    var count by remember { mutableStateOf(0) }
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(
                text = "You've had $count glasses.",
                modifier = modifier.padding(16.dp)
            )
        }
        Button(
            onClick = {
                count += 1
                Log.v("BasicStateAppTag", "onClick() called")
            },
            modifier = Modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Log.v("BasicStateAppTag", "WaterCounter.Button contents composed")
            Text(text = "Add one")
        }
    }
}