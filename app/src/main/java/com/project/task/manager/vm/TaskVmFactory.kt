package com.project.task.manager.vm

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.project.task.manager.repository.Repository

class TaskVmFactory(private val repository: Repository, private val application: Application) : ViewModelProvider.Factory {
  /*  override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return *//*MainViewModel( application)*//* T
    }*/
}