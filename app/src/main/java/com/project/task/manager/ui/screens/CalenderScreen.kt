package com.project.task.manager.ui.screens

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.project.task.manager.R
import com.project.task.manager.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(stringResource(id = R.string.calender))

    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    DatePicker(
        state = datePickerState, modifier,
        title = {
            Text(text = "Gregorian Calender")
        },
    )

}