package com.project.task.manager.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import com.project.task.manager.R
import ir.kaaveh.sdpcompose.sdp
import java.time.LocalDateTime

private const val TAG = "Components"

@Composable
fun TaskImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = description,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    onSubmit: (tittle: String, desc: String, time: String, createdOn: String) -> Unit
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

    val sheetState = rememberSheetState()

    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Card(
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(5.sdp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.sdp)
            ) {
                val (taskTittle,
                    taskDesc,
                    dialogTitle,
                    taskTime,
                    taskSaveBtn
                ) = createRefs()
                Text(
                    modifier = Modifier.constrainAs(dialogTitle) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }, text = stringResource(R.string.add_your_task),
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedTextField(modifier = Modifier.constrainAs(taskTittle) {
                    start.linkTo(parent.start)
                    top.linkTo(dialogTitle.bottom)
                    end.linkTo(parent.end)
                }, value = tittleState,
                    onValueChange = {
                        tittleState = it
                    }, label = {
                        Text(text = stringResource(id = R.string.task_tittle))
                    })
                OutlinedTextField(modifier = Modifier.constrainAs(taskDesc) {
                    start.linkTo(taskTittle.start)
                    top.linkTo(taskTittle.bottom)
                    end.linkTo(taskTittle.end)
                }, value = descState,
                    onValueChange = {
                        descState = it
                    }, label = {
                        Text(text = stringResource(id = R.string.task_desc))
                    })
                OutlinedTextField(modifier = Modifier
                    .constrainAs(taskTime) {
                        start.linkTo(taskTittle.start)
                        top.linkTo(taskDesc.bottom)
                        end.linkTo(taskTittle.end)
                    }, value = dateTimeState,
                    onValueChange = {
                        dateTimeState = it
                    }, readOnly = true,
                    interactionSource = inputSourceState,
                    label = {
                        Text(text = stringResource(id = R.string.task_time))
                    })


                Button(
                    modifier = Modifier
                        .padding(top = 10.sdp)
                        .constrainAs(taskSaveBtn) {
                            start.linkTo(parent.start)
                            top.linkTo(taskTime.bottom)
                            end.linkTo(parent.end)
                        },
                    onClick = {
                        val createdOn = LocalDateTime.now().toString()
                        if (tittleState.isNotEmpty() && descState.isNotEmpty() && dateTimeState.isNotEmpty()) {
                            onSubmit.invoke(tittleState, descState, dateTimeState, createdOn)
                        }
                    },
                ) {
                    Text(text = stringResource(R.string.set_task_reminder))
                }
            }
            DateTimePickerDialog(sheetState) {
                dateTimeState = it.toString()
                Log.d(TAG, "TaskInputDialog: $it")
            }
            if (inputSourceState.collectIsPressedAsState().value) {
                sheetState.show()
            }

        }

    }

}


@Composable
fun DateTimePickerDialog(state: SheetState, onDateChange: (LocalDateTime) -> Unit) {

    val current = LocalDateTime.now()
    DateTimeDialog(
        state = state,
        config = DateTimeConfig(minYear = current.year, maxYear = current.year + 5),
        header = Header.Default(stringResource(id = R.string.task_time), icon = IconSource(painter = painterResource(id = R.drawable.ic_calender))),
        selection = DateTimeSelection.DateTime {
            onDateChange.invoke(it)
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}
