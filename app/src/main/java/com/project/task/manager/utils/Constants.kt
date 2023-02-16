package com.project.task.manager.utils

import com.project.task.manager.models.Task

object Constants {
    const val TASK_TABLE = "task_table"
    const val COMPLETED_TASKS = "completed_tasks"
    const val PENDING_TASKS = "pending_task"
    const val DATABASE = "task_db.db"
    const val HOME_SCREEN = "Home"
    const val CURRENT_SCREEN = "current_screen"
    val PREVIEW_TASK = Task(1, "Exercise", "Exercise keeps body healthy", "2028-04-02T01:01", "2028-04-02T01:01")
}