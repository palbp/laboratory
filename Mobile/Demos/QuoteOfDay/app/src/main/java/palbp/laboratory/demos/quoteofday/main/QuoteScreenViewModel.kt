package palbp.laboratory.demos.quoteofday.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import palbp.laboratory.demos.quoteofday.utils.loggableMutableStateOf

class QuoteScreenViewModel(private val quoteService: QuoteService): ViewModel() {

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
}

