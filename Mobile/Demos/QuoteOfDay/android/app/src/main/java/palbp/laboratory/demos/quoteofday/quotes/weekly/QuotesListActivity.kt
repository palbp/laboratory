package palbp.laboratory.demos.quoteofday.quotes.weekly

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import palbp.laboratory.demos.quoteofday.info.InfoActivity

class QuotesListActivity : ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, QuotesListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesListScreen(
                onBackRequested = { finish() },
                onInfoRequest = { InfoActivity.navigate(origin = this) }
            )
        }
    }
}