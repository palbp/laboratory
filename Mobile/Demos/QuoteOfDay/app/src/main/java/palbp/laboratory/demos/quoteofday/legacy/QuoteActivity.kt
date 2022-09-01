package palbp.laboratory.demos.quoteofday.legacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import palbp.laboratory.demos.quoteofday.LoadingState
import palbp.laboratory.demos.quoteofday.R

class QuoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quote_legacy)

        val fetchButton = findViewById<LoadingButtonLegacy>(R.id.fetchButton)
        fetchButton.setOnClickListener {
            fetchButton.loadingState = LoadingButtonLegacy.State.Loading
        }
    }
}