package com.project.task.manager.di

import android.content.Context
import androidx.room.Room
import com.project.task.manager.db.TaskDatabase
import com.project.task.manager.repository.Repository
import com.project.task.manager.repository.RepositoryImpl
import com.project.task.manager.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModules {

    @Provides
    fun getDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, Constants.DATABASE).build()
    }


    @Provides
    fun getRepository(taskDatabase: TaskDatabase): Repository = RepositoryImpl(taskDatabase)
}