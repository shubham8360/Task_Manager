package com.project.task.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.task.manager.ui.screens.CalenderScreen
import com.project.task.manager.ui.screens.CompletedTaskScreen
import com.project.task.manager.ui.screens.TaskScreen
import com.project.task.manager.vm.MainViewModel

@Preview
@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    val viewModel = viewModel<MainViewModel>()
    NavHost(
        navController = navHostController, startDestination = BottomBarScreens.TaskScreen.route
    ) {
        composable(BottomBarScreens.TaskScreen.route) {
            TaskScreen(viewModel = viewModel, modifier = modifier)
        }

        composable(BottomBarScreens.CompletedTaskScreen.route) {
            CompletedTaskScreen(modifier = modifier, viewModel = viewModel)
        }
        composable(BottomBarScreens.CalenderScreen.route) {
            CalenderScreen(modifier = modifier, viewModel = viewModel)
        }
    }
}