package com.project.task.manager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.task.manager.screens.CalenderScreen
import com.project.task.manager.screens.CompletedTaskScreen
import com.project.task.manager.screens.TaskScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.TaskScreen.route
    ) {
        composable(BottomBarScreen.TaskScreen.route) {
            TaskScreen()
        }
        composable(BottomBarScreen.CalenderScreen.route) {
            CalenderScreen()
        }
        composable(BottomBarScreen.CompletedTaskScreen.route) {
            CompletedTaskScreen()
        }
    }
}