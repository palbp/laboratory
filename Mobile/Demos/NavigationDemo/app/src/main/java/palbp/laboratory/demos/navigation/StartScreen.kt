package palbp.laboratory.demos.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import palbp.laboratory.demos.navigation.ui.theme.NavigationDemoTheme

const val startScreenRoute = "start"

@Composable
fun StartScreen(
    onStartIntent: () -> Unit,
    viewModel: StartScreenViewModel = viewModel()
) {
    StartScreenView(onStartIntent = onStartIntent)
}

@Composable
fun StartScreenView(onStartIntent: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(text = "Main Screen")
            Button(onClick = onStartIntent) {
                Text(text = "Start")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenViewPreview() {
    NavigationDemoTheme {
        StartScreenView(onStartIntent = {})
    }
}

class StartScreenViewModel : ViewModel() {
    init {
        Log.v(TAG, "StartScreenViewModel init ${hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "StartScreenViewModel.onCleared() ${hashCode()}")
    }

    fun doIt() {
        viewModelScope.launch {

        }
    }
}