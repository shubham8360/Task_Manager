package com.project.task.manager.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.repository.Repository
import com.project.task.manager.utils.Constants.COMPLETED_TASKS
import com.project.task.manager.utils.Constants.CURRENT_SCREEN
import com.project.task.manager.utils.Constants.PENDING_TASKS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
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

    val firebaseRef=Firebase.database.getReference(Task::class.simpleName!!)


    init {
        getAllTask()
    }

    fun setCurrentScreen(tittle: String) {
        savedStateHandle[CURRENT_SCREEN] = tittle
    }

    private fun getAllTask() {
        viewModelScope.launch {
            taskRepository.getAllTasks().flowOn(Dispatchers.IO).collect { newList ->
                val sortedList = newList.sortedBy { LocalDateTime.parse(it.taskTime).second }
                val partitionedList = sortedList.partition { it.isCompleted }
                savedStateHandle[COMPLETED_TASKS] = partitionedList.first.sortedBy { LocalDateTime.parse(it.taskTime).second }
                savedStateHandle[PENDING_TASKS] = partitionedList.second
            }

        }
    }

    fun upsertTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                taskRepository.upsertTask(task)
                firebaseRef.push().setValue(task)
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
