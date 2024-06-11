package palbp.laboratory.demos.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

const val findTabRoute = "find"

@Composable
fun FindTab(
    viewModel: FindTabViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // TODO
}

class FindTabViewModel : ViewModel() {
    init {
        Log.v(TAG, "FindTabViewModel init ${hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "FindTabViewModel.onCleared() ${hashCode()}")
    }
}