package com.project.task.manager.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.task.manager.R
import com.project.task.manager.components.TaskInputDialog
import com.project.task.manager.models.Task
import com.project.task.manager.vm.MainViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TaskScreen(viewModel: MainViewModel = viewModel(), modifier: Modifier) {
    viewModel.setCurrentScreen(stringResource(id = R.string.home))
    val dialogState = viewModel.inputDialogState.collectAsState()

    val tasksState = viewModel.tasks.collectAsState()
    LazyColumn(modifier = modifier) {
        items(tasksState.value){
            TaskListItem(it)
            Divider(Modifier.padding(vertical = 3.sdp), color = Color.Transparent)
        }
    }
    if (dialogState.value) {
        TaskInputDialog(
            onSubmit = {  tittle, desc, time, createdOn ->
                viewModel.upsertTask(Task(tittle=tittle, description = desc, taskTime = time, createdOn =  createdOn))
                viewModel.inputDialogState.value = !dialogState.value
            }, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true),
            onDismissRequest = { viewModel.inputDialogState.value = !dialogState.value })
    }

}

@Preview
@Composable
fun TaskListItem(task: Task = Task(1, "Exercise", "Exercise keeps body healthy", "Task Time", "Created On")) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.sdp)
            .wrapContentHeight()
    ) {
        var checkedState by remember {
            mutableStateOf(false)
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.sdp)
        ) {
            val (taskTittle,
                taskDesc,
                taskCreatedOn,
                taskTime,
                taskStatus
            ) = createRefs()
            Checkbox(modifier = Modifier.constrainAs(taskStatus) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, checked = checkedState, onCheckedChange = {
                checkedState = it
            })

            Text(text = task.tittle,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(taskTittle) {
                    start.linkTo(taskStatus.end)
                    top.linkTo(parent.top)
                })

            Text(text = task.description, modifier = Modifier.constrainAs(taskDesc) {
                start.linkTo(taskTittle.start)
                top.linkTo(taskTittle.bottom)
            })

            Text(text = task.taskTime, modifier = Modifier.constrainAs(taskTime) {
                start.linkTo(taskTittle.start)
                top.linkTo(taskDesc.bottom)
            })

            Text(text = task.createdOn,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 5.sdp, vertical = 5.sdp)
                    .constrainAs(taskCreatedOn) {
                        end.linkTo(parent.end)
                    })
        }
    }
}