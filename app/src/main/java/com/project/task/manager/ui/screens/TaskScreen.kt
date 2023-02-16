package com.project.task.manager.ui.screens

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.ui.components.TaskImage
import com.project.task.manager.ui.components.TaskInputDialog
import com.project.task.manager.utils.Constants
import com.project.task.manager.utils.formatTaskTime
import com.project.task.manager.vm.MainViewModel
import ir.kaaveh.sdpcompose.sdp


@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {

    viewModel.setCurrentScreen(stringResource(id = R.string.home))
    val dialogState = viewModel.inputDialogState.collectAsState()
    val taskListState = viewModel.pendingTasks.collectAsState()
//    val listOfTasks = taskListState.value.filter { !it.isCompleted }

    LazyColumn(modifier = modifier) {
        items(taskListState.value) {
            TaskListItem(task = it) { newTask ->
                viewModel.upsertTask(task = newTask)
            }
            Divider(Modifier.padding(vertical = 3.sdp), color = Color.Transparent)
        }
    }
    if (dialogState.value) {
        TaskInputDialog(onSubmit = { tittle, desc, time, createdOn ->
            viewModel.upsertTask(
                Task(
                    tittle = tittle, description = desc, taskTime = time, createdOn = createdOn
                )
            )
            viewModel.inputDialogState.value = !dialogState.value
        },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true),
            onDismissRequest = { viewModel.inputDialogState.value = !dialogState.value }, onCancel = {
                viewModel.inputDialogState.value = !dialogState.value
            }
        )
    }

}

@Preview
@Composable
fun TaskListItem(
    task: Task = Constants.PREVIEW_TASK, onTaskStatusChange: (updatedTask: Task) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.sdp)
            .wrapContentHeight()
    ) {
        var checkedState by rememberSaveable {
            mutableStateOf(false)
        }
        checkedState = task.isCompleted
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.sdp)
        ) {
            val (taskTittleTv, taskDescTv, taskCreatedOnTv, taskTimeTv, taskStatusCB, alarmIndicatorIv) = createRefs()

            Checkbox(modifier = Modifier.constrainAs(taskStatusCB) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, checked = checkedState, onCheckedChange = {
                checkedState = it
                val newTask = task.apply {
                    isCompleted = checkedState
                }
                onTaskStatusChange(newTask)
            })

            Text(
                text = task.tittle, style = MaterialTheme.typography.titleLarge, modifier = Modifier.constrainAs(taskTittleTv) {
                    start.linkTo(taskStatusCB.end)
                    top.linkTo(parent.top)
                }, textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
            )

            Text(
                text = task.description,
                modifier = Modifier.constrainAs(taskDescTv) {
                    start.linkTo(taskTittleTv.start)
                    top.linkTo(taskTittleTv.bottom)
                },
                textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = task.taskTime.formatTaskTime(), modifier = Modifier.constrainAs(taskTimeTv) {
                    start.linkTo(taskTittleTv.start)
                    top.linkTo(taskDescTv.bottom)
                }, textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = task.createdOn,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 5.sdp, vertical = 5.sdp)
                    .constrainAs(taskCreatedOnTv) {
                        end.linkTo(parent.end)
                    },
                textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
            )
            TaskImage(
                modifier = Modifier.constrainAs(alarmIndicatorIv) {
                    end.linkTo(taskCreatedOnTv.end)
                    start.linkTo(taskCreatedOnTv.start)
                    top.linkTo(taskCreatedOnTv.bottom)
                    bottom.linkTo(parent.bottom)
                },
                painter = painterResource(id = if (checkedState) R.drawable.ic_checked else R.drawable.ic_alarm),
                description = stringResource(R.string.alarm_indicator_icon_cd),
                colorFilter = ColorFilter.tint(
                    if (checkedState) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }

}