package palbp.laboratory.demos.quoteofday.daily

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import palbp.laboratory.demos.quoteofday.TAG
import palbp.laboratory.demos.quoteofday.utils.loggableMutableStateOf

class QuoteScreenViewModel(private val quoteService: QuoteService): ViewModel() {

    init {
        Log.v(TAG, "QuoteScreenViewModel.init()")
    }

    val isLoading: Boolean
        get() = _isLoading

    private var _isLoading by
        loggableMutableStateOf("QuoteScreenViewModel.isLoading", false)

    val quote: Quote?
        get() = _quote

    private var _quote by
        loggableMutableStateOf<Quote?>("QuoteScreenViewModel.quote", null)

    fun fetchQuote() {
        viewModelScope.launch {
            _isLoading = true
            _quote = quoteService.fetchQuote()
            _isLoading = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "QuoteScreenViewModel.onCleared()")
    }
}
