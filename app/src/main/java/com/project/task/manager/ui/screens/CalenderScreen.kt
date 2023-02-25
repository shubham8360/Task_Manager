package com.project.task.manager.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.task.manager.R
import com.project.task.manager.vm.MainViewModel

@Composable
fun CalenderScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(stringResource(id = R.string.calender))

    ConstraintLayout {

    }
}