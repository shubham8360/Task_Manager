package com.project.task.manager.repository

import com.project.task.manager.db.TaskDatabase
import com.project.task.manager.models.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
Solid Guidelines -> abstraction
 */

interface Repository {
    suspend fun getAllTasks(): Flow<List<Task>>
    suspend fun upsertTask(task: Task): Long
    suspend fun deleteTask(task: Task)
}

class RepositoryImpl @Inject constructor(private val taskDatabase: TaskDatabase) : Repository {

    override suspend fun getAllTasks(): Flow<List<Task>> = taskDatabase.getTaskDao().getAll()

    override suspend fun upsertTask(task: Task) = taskDatabase.getTaskDao().upsertTask(task)

    override suspend fun deleteTask(task: Task) = taskDatabase.getTaskDao().removeTask(task)

}