package palbp.laboratory.demos.quoteofday.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import palbp.laboratory.demos.quoteofday.TAG

class LoggedMutableState<T>(private val delegate: MutableState<T>)
    : MutableState<T> by delegate {

    override var value: T
        get() {
            Log.v(TAG, "$TAG: read value = ${delegate.value}")
            return delegate.value
        }
        set(value) {
            Log.v(TAG, "$TAG: write value = $value")
            delegate.value = value
        }
}

fun <T> loggableMutableStateOf(value: T): MutableState<T> =
    LoggedMutableState(mutableStateOf(value))

