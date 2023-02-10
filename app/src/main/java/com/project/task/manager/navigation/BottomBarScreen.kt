package com.project.task.manager.navigation

import com.project.task.manager.R

sealed class BottomBarScreen(
    val route: String,
    val tittle: String,
    val icon: Int
) {
    object TaskScreen : BottomBarScreen(
        route = "task_screen",
        tittle = "Tasks",
        icon = R.drawable.ic_task_list
    )

    object CalenderScreen : BottomBarScreen(
        route = "calender_screen",
        tittle = "Calender",
        icon =  R.drawable.ic_calender
    )

    object CompletedTaskScreen : BottomBarScreen(
        route = "completed_task_screen",
        tittle = "Completed Tasks",
        icon =  R.drawable.ic_completed_task
    )
}