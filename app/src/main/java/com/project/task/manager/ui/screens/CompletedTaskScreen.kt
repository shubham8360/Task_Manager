package com.project.task.manager.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.ui.components.TaskListItem
import com.project.task.manager.vm.MainViewModel
import ir.kaaveh.sdpcompose.sdp

@Preview
@Composable
fun CompletedTaskScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    viewModel.setCurrentScreen(stringResource(id = R.string.task_completed))
    val taskListState = viewModel.completedTasks.collectAsState()
//    val filteredList = taskListState.value.filter { it.isCompleted }
    Surface {
        LazyColumn(modifier = modifier) {
            items(taskListState.value, key = { item: Task -> item.id }) {
                TaskListItem(task = it) { newTask ->
                    viewModel.upsertTask(task = newTask)
                }
                Divider(Modifier.padding(vertical = 3.sdp), color = Color.Transparent)
            }
        }
    }

}