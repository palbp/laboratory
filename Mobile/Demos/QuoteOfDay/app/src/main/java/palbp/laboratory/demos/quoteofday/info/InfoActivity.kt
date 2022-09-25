package palbp.laboratory.demos.quoteofday.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import palbp.laboratory.demos.quoteofday.R
import palbp.laboratory.demos.quoteofday.TAG

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfoScreen(
                onBackRequested = { finish() },
                onSendEmailRequested = { openSendEmail() },
                onOpenUrlRequested = { openURL(it) },
                socials = socialLinks
            )
        }
    }

    private fun openSendEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, authorEmail)
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        }

        val chooser = Intent.createChooser(
            intent,
            resources.getString(R.string.activity_info_chooser_title)
        )

        if (intent.resolveActivity(packageManager) != null)
            startActivity(chooser)
    }

    private fun openURL(url: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Failed to open URL", e)
            Toast
                .makeText(
                    this,
                    R.string.activity_info_no_browser_error,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }
}

private val socialLinks = listOf(
    SocialInfo(
        link = Uri.parse("https://www.linkedin.com/in/palbp/"),
        imageId = R.drawable.ic_linkedin
    ),
    SocialInfo(
        link = Uri.parse("https://www.twitch.tv/paulo_pereira"),
        imageId = R.drawable.ic_twitch
    ),
    SocialInfo(
        link = Uri.parse("https://www.youtube.com/channel/UCetmdF6qGnMAdZP32i8AnbA"),
        imageId = R.drawable.ic_youtube
    )
)

private const val authorEmail = "paulo.pereira@isel.pt"
private const val emailSubject = "About the QuoteOfDay App"
