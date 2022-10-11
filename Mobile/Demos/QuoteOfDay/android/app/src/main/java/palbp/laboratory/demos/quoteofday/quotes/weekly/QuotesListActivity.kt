package palbp.laboratory.demos.quoteofday.quotes.weekly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class QuotesListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesListScreen()
        }
    }
}