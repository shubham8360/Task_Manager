package com.project.task.manager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.task.manager.models.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
}