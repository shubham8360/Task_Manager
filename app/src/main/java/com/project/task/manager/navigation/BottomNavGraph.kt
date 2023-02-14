package com.project.task.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.task.manager.screens.CalenderScreen
import com.project.task.manager.screens.CompletedTaskScreen
import com.project.task.manager.screens.TaskScreen
import com.project.task.manager.vm.MainViewModel

@Preview
@Composable
fun BottomNavGraph(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<MainViewModel>()
    NavHost(
        navController = navHostController, startDestination = BottomBarScreens.TaskScreen.route
    ) {
        composable(BottomBarScreens.TaskScreen.route) {
            TaskScreen(viewModel, modifier)
        }
        composable(BottomBarScreens.CalenderScreen.route) {
            CalenderScreen(viewModel, modifier)
        }
        composable(BottomBarScreens.CompletedTaskScreen.route) {
            CompletedTaskScreen(viewModel, modifier)
        }
    }
}