package com.project.task.manager.ui.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.project.task.manager.R
import com.project.task.manager.ui.theme.TaskManagerTheme
import com.project.task.manager.utils.Constants
import com.project.task.manager.utils.timePickerDialog
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private const val TAG = "Dialogs"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    onSubmit: (tittle: String, desc: String, time: String, createdOn: String) -> Unit,
    onCancel: () -> Unit
) {
    var tittleState by remember {
        mutableStateOf("")
    }
    var descState by remember {
        mutableStateOf("")
    }
    var dateTimeState by remember {
        mutableStateOf("")
    }
    val inputSourceState = remember {
        MutableInteractionSource()
    }

    val scope = rememberCoroutineScope()

    var dateTimeInputDialogState by remember {
        mutableStateOf(false)
    }
    var clockState by remember {
        mutableStateOf(false)
    }
    val currentTime = LocalDateTime.now()
    val dateRangePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis(),
            yearRange = IntRange(currentTime.year, currentTime.year.plus(2)),
        )

    val context = LocalContext.current

    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Card(
            shape = RoundedCornerShape(13.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            elevation = CardDefaults.cardElevation(5.sdp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {

                TaskImage(
                    painter = painterResource(id = R.drawable.ic_event_note),
                    description = stringResource(R.string.event_note_taking_cd),
                    modifier = Modifier
                        .padding(vertical = 10.sdp)
                        .align(Alignment.CenterHorizontally)
                        .size(40.sdp)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 20.sdp),
                    text = stringResource(R.string.add_your_task),
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    value = tittleState,
                    onValueChange = {
                        tittleState = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.task_tittle))
                    },
                    singleLine = true
                )
                OutlinedTextField(
                    value = descState,
                    onValueChange = {
                        descState = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.task_desc))
                    }, maxLines = 3
                )
                OutlinedTextField(
                    value = dateTimeState,
                    onValueChange = {
                        dateTimeState = it
                    },
                    readOnly = true,
                    interactionSource = inputSourceState,
                    label = {
                        Text(text = stringResource(id = R.string.task_time))
                    },
                    singleLine = true
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 10.sdp, horizontal = 5.sdp)
                        .align(Alignment.End)
                ) {
                    Button(
                        modifier = Modifier.padding(horizontal = 5.sdp),
                        onClick = { onCancel.invoke() }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                val createdOn = LocalDateTime.now().toLocalDate().toString()
                                if (tittleState.isNotEmpty() && descState.isNotEmpty() && dateTimeState.isNotEmpty()) {
                                    onSubmit.invoke(
                                        tittleState,
                                        descState,
                                        dateTimeState,
                                        createdOn
                                    )
                                }
                            }

                        },
                    ) {
                        Text(text = stringResource(R.string.save))
                    }

                }
            }


            if (dateTimeInputDialogState) {
                DatePickerDialog(
                    onDismissRequest = { dateTimeInputDialogState = !dateTimeInputDialogState },
                    dismissButton = {
                        Button(onClick = { dateTimeInputDialogState = !dateTimeInputDialogState }) {
                            Text(text = "Cancel")
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            scope.launch {
                                Log.d(
                                    TAG,
                                    "TaskInputDialog:${dateRangePickerState.selectedDateMillis} "
                                )
                                dateTimeState = LocalDateTime.ofInstant(
                                    dateRangePickerState.selectedDateMillis?.let {
                                        Instant.ofEpochMilli(
                                            it
                                        )
                                    },
                                    ZoneId.systemDefault()
                                ).toString()
                                dateTimeInputDialogState = !dateTimeInputDialogState
                            }
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                ) {
                    DatePicker(state = dateRangePickerState)
                }
            }
            if (clockState) {
                timePickerDialog(context) {
                    val localDateTime = LocalDateTime.of(Constants.TEMP_DAY, it)
                    dateTimeState = localDateTime.toString()
                    clockState = !clockState
                }.show()
            }

            if (inputSourceState.collectIsPressedAsState().value) {
                dateTimeInputDialogState = true
            }

        }


    }

}

@Preview
@Composable
private fun DefaultPreviewTaskInputDialog() {
    TaskManagerTheme {
        TaskInputDialog(onDismissRequest = {}, onSubmit = { _, _, _, _ -> }) {

        }
    }
}