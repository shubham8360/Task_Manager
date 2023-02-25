package com.project.task.manager.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.ui.components.TaskInputDialog
import com.project.task.manager.ui.components.TaskListItem
import com.project.task.manager.vm.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch


@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {

    viewModel.setCurrentScreen(stringResource(id = R.string.home))
    val dialogState = viewModel.inputDialogState.collectAsState()
    val taskListState = viewModel.pendingTasks.collectAsState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Surface {
        LazyColumn(modifier = modifier) {
            items(taskListState.value, key = { item: Task -> item.id }) {
                TaskListItem(task = it) { newTask ->
                    viewModel.upsertTask(task = newTask)
                }
                Divider(Modifier.padding(vertical = 3.sdp), color = Color.Transparent)
            }
        }
        SnackbarHost(hostState = snackBarHostState)
        if (dialogState.value) {
            TaskInputDialog(onSubmit = { tittle, desc, time, createdOn ->
                scope.launch {
                    viewModel.upsertTask(
                        Task(
                            tittle = tittle,
                            description = desc,
                            taskTime = time,
                            createdOn = createdOn
                        )
                    )
                    viewModel.inputDialogState.value = !dialogState.value
                    snackBarHostState.showSnackbar("Saved")
                }
            },
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true),
                onDismissRequest = { viewModel.inputDialogState.value = !dialogState.value }, onCancel = {
                    viewModel.inputDialogState.value = !dialogState.value
                }
            )
        }
    }


}

