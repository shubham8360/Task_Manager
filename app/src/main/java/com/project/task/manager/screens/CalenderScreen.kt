package com.project.task.manager.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.project.task.manager.R
import com.project.task.manager.vm.MainViewModel

@Composable
fun CalenderScreen(viewModel: MainViewModel, modifier: Modifier) {
    viewModel.setCurrentScreen(stringResource(id = R.string.calender))
}