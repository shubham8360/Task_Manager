package com.project.task.manager.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.task.manager.R
import com.project.task.manager.models.Task
import com.project.task.manager.utils.Constants
import com.project.task.manager.utils.capitalizeString
import com.project.task.manager.utils.formatTaskTime

@Preview
@Composable
fun TaskListItem(
    task: Task = Constants.PREVIEW_TASK, onTaskStatusChange: (updatedTask: Task) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var checkedState by rememberSaveable {
            mutableStateOf(false)
        }
        checkedState = task.isCompleted
        Row(modifier = Modifier.padding(5.dp)) {
            Checkbox(
                modifier = Modifier.align(Alignment.CenterVertically),
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    val newTask = task.apply {
                        isCompleted = checkedState
                    }
                    onTaskStatusChange(newTask)
                }
            )
            Column(
                modifier = Modifier.weight(0.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.tittle.capitalizeString(),
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = task.description,
                    textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = task.taskTime.formatTaskTime(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Light,
                    textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
                )
            }
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                TaskImage(
                    Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = if (checkedState) R.drawable.ic_checked else R.drawable.ic_alarm),
                    description = stringResource(R.string.alarm_indicator_icon_cd),
                    colorFilter = ColorFilter.tint(
                        if (checkedState) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = task.createdOn,
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = if (checkedState) TextDecoration.LineThrough else TextDecoration.None
                )
            }

        }
    }

}