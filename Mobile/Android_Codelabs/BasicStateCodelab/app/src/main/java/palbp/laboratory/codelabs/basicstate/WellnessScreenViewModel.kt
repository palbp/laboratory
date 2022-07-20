package palbp.laboratory.codelabs.basicstate

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessScreenViewModel : ViewModel() {

    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }
}


private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
