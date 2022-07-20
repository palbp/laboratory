package palbp.laboratory.codelabs.basicstate

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessScreenViewModel
) {
    Column(modifier = modifier) {
        WaterCounter(modifier)
        WellnessTaskList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) }
        )
    }
}

