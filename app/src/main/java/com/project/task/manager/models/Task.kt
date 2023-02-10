package com.project.task.manager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val tittle: String,
    val description: String,
    val time: String
) : Parcelable