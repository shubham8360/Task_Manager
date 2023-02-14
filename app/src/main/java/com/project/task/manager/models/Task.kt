package com.project.task.manager.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.task.manager.utils.Constants.TASK_TABLE
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
@Entity(tableName = TASK_TABLE)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = Random.Default.nextInt(0,10000000),
    val tittle: String,
    val description: String,
    val taskTime: String,
    val createdOn: String
) : Parcelable