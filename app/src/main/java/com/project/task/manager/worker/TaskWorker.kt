package com.project.task.manager.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.project.task.manager.R
import com.project.task.manager.utils.Constants.DOWNLOAD_CHANNEL
import kotlin.random.Random

class TaskWorker(
    private val context: Context,
    private val workerParams: WorkerParameters,
    private val notifyToUser: () -> Unit
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        notifyToUser.invoke()
        startForegroundService()
        return Result.success()
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, DOWNLOAD_CHANNEL)
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentText("Task time comes up, here is reminder.")
                    .setContentTitle("Pending Task")
                    .build()
            )
        )
    }


}