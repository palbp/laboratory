package palbp.laboratory.demos.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

const val homeTabRoute = "home"

@Composable
fun HomeTab(
    viewModel: HomeTabViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // TODO
}

class HomeTabViewModel : ViewModel() {
    init {
        Log.v(TAG, "HomeTabViewModel init ${hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "HomeTabViewModel.onCleared() ${hashCode()}")
    }
}
