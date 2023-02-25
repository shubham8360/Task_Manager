package com.project.task.manager.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.repository.Repository
import com.project.task.manager.utils.Constants.COMPLETED_TASKS
import com.project.task.manager.utils.Constants.CURRENT_SCREEN
import com.project.task.manager.utils.Constants.PENDING_TASKS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: Repository,
    app: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    val completedTasks = savedStateHandle.getStateFlow(COMPLETED_TASKS, emptyList<Task>())
    val pendingTasks = savedStateHandle.getStateFlow(PENDING_TASKS, emptyList<Task>())
    var inputDialogState = MutableStateFlow(false)
    var screen = savedStateHandle.getStateFlow(CURRENT_SCREEN, app.getString(R.string.home))

    init {
        getAllTask()
    }

    fun setCurrentScreen(tittle: String) {
        savedStateHandle[CURRENT_SCREEN] = tittle
    }

    private fun getAllTask() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                taskRepository.getAllTasks().collect { newList ->
                    val partitionedList = newList.partition { it.isCompleted }
                    savedStateHandle[COMPLETED_TASKS] = partitionedList.first
                    savedStateHandle[PENDING_TASKS] = partitionedList.second
                }
            }

        }
    }

    fun upsertTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                taskRepository.upsertTask(task)
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
