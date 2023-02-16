package com.project.task.manager.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.maxkeppeker.sheets.core.icons.LibIcons
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.project.task.manager.R
import com.project.task.manager.ui.theme.TaskManagerTheme
import ir.kaaveh.sdpcompose.sdp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private const val TAG = "Components"

@Composable
fun TaskImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    description: String,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier
            .size(25.sdp)
            .clip(CircleShape),
        painter = painter,
        contentDescription = description,
        colorFilter = colorFilter
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    onSubmit: (tittle: String, desc: String, time: String, createdOn: String) -> Unit, onCancel: () -> Unit
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


    val calenderState = rememberSheetState()
    val clockState = rememberSheetState()

    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Card(
            shape = RoundedCornerShape(13.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            elevation = CardDefaults.cardElevation(5.sdp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                val (taskTittleTv,
                    taskDescTv,
                    dialogTitleTv,
                    taskTimeTv,
                    saveBtn,
                    eventIndicatorIv,
                    cancelBtn
                ) = createRefs()
                TaskImage(
                    painter = painterResource(id = R.drawable.ic_event_note),
                    description = stringResource(R.string.event_note_taking_cd),
                    modifier = Modifier
                        .constrainAs(eventIndicatorIv) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                        }
                        .padding(vertical = 10.sdp)
                        .size(40.sdp)
                )
                Text(
                    modifier = Modifier
                        .constrainAs(dialogTitleTv) {
                            start.linkTo(parent.start)
                            top.linkTo(eventIndicatorIv.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(bottom = 20.sdp),
                    text = stringResource(R.string.add_your_task),
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(taskTittleTv) {
                            start.linkTo(parent.start)
                            top.linkTo(dialogTitleTv.bottom)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
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
                    modifier = Modifier.constrainAs(taskDescTv) {
                        start.linkTo(taskTittleTv.start)
                        top.linkTo(taskTittleTv.bottom)
                        end.linkTo(taskTittleTv.end)
                        width = Dimension.fillToConstraints
                    },
                    value = descState,
                    onValueChange = {
                        descState = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.task_desc))
                    }, maxLines = 3
                )
                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(taskTimeTv) {
                            start.linkTo(taskTittleTv.start)
                            top.linkTo(taskDescTv.bottom)
                            end.linkTo(taskTittleTv.end)
                            width = Dimension.fillToConstraints
                        }, value = dateTimeState,
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


                Button(
                    modifier = Modifier
                        .padding(vertical = 10.sdp)
                        .constrainAs(saveBtn) {
                            top.linkTo(taskTimeTv.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    onClick = {
                        val createdOn = LocalDateTime.now().toLocalDate().toString()
                        if (tittleState.isNotEmpty() && descState.isNotEmpty() && dateTimeState.isNotEmpty()) {
                            onSubmit.invoke(tittleState, descState, dateTimeState, createdOn)
                        }
                    },
                ) {
                    Text(text = stringResource(R.string.save))
                }
                Button(modifier = Modifier
                    .padding(10.sdp)
                    .constrainAs(cancelBtn) {
                        end.linkTo(saveBtn.start)
                        top.linkTo(saveBtn.top)
                        bottom.linkTo(saveBtn.bottom)
                    },
                    onClick = { onCancel.invoke() }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
            var localSelectedDate: LocalDate = LocalDate.now()
            CustomCalenderDialog(state = calenderState, onDateChange = {
                localSelectedDate = it
                clockState.show()
            })
            CustomClockDialog(state = clockState,
                onTimeSelect = { selectedLocalTime ->
                    val localDateTime = LocalDateTime.of(localSelectedDate, selectedLocalTime)
                    dateTimeState = localDateTime.toString()
                })

            if (inputSourceState.collectIsPressedAsState().value) {
                Log.d(TAG, "TaskInputDialog: $inputSourceState")
                calenderState.show()
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCalenderDialog(state: SheetState, onDateChange: (LocalDate) -> Unit) {
    val current = LocalDateTime.now()
    CalendarDialog(
        state = state,
        selection = CalendarSelection.Date {
            onDateChange(it)
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            minYear = current.year,
            maxYear = current.year + 2
        ),
        header = Header.Default(
            title = stringResource(id = R.string.calender),
            icon = IconSource(
                painter = painterResource(id = R.drawable.ic_calender),
                contentDescription = stringResource(R.string.calender_icon_in_dialog_cd)
            )
        ),
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomClockDialog(state: SheetState, onTimeSelect: (LocalTime) -> Unit) {
    ClockDialog(
        state = state,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            val time = LocalTime.of(hours, minutes)
            onTimeSelect(time)
        },
        config = ClockConfig(
            defaultTime = LocalTime.now(),
            is24HourFormat = false,
            icons = LibIcons.TwoTone
        ),
        header = Header.Default(
            "Set Alarm",
            IconSource(
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = stringResource(R.string.set_alarm_icon_cd)
            )
        ), properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier,
    tittle: String,
    scrollState: TopAppBarScrollBehavior,
    icon: @Composable () -> Unit
) {
    TopAppBar(
        modifier = modifier, title = {
            Text(
                text = tittle,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = icon,
        colors = TopAppBarDefaults.mediumTopAppBarColors(),
        scrollBehavior = scrollState
    )
}



@Preview
@Composable
private fun DefaultPreviewTaskInputDialog() {
    TaskManagerTheme {
        TaskInputDialog(onDismissRequest = { }, onSubmit = { _, _, _, _ -> }) {

        }
    }
}
@Preview(showBackground = true)
@Composable
private fun DefaultPreviewTaskImage() {
    TaskManagerTheme {
        TaskImage(painter = painterResource(id = R.drawable.ic_alarm), description ="" )
    }
}
