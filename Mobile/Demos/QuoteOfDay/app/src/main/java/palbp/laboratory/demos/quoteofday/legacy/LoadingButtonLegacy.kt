package palbp.laboratory.demos.quoteofday.legacy

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import palbp.laboratory.demos.quoteofday.R

class LoadingButtonLegacy(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : AppCompatButton(context, attrs, defStyleAttr) {

    enum class State { Idle, Loading }

    var loadingState: State = State.Idle
        set(value) {
            field = value
            updateText()
        }

    private fun updateText() {
        text = context.getText(
            if(loadingState == State.Idle) R.string.fetch_button_text_idle
            else R.string.fetch_button_text_loading
        )
    }

    init { updateText() }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, androidx.appcompat.R.attr.buttonStyle)
    constructor(context: Context) : this(context, attrs = null, defStyleAttr = androidx.appcompat.R.attr.buttonStyle)
}