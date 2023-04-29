package com.project.task.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.task.manager.db.TaskDatabase
import com.project.task.manager.repository.Repository
import com.project.task.manager.ui.screens.MainScreen
import com.project.task.manager.ui.theme.TaskManagerTheme
import com.project.task.manager.vm.MainViewModel
import com.project.task.manager.worker.TaskWorker
import dagger.hilt.android.AndroidEntryPoint
import ir.kaaveh.sdpcompose.ssp
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var roomDatabase: TaskDatabase

    @Inject
    lateinit var imp: Repository

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TaskManagerTheme {
                MainScreen(mainViewModel)
            }

        }
     /*   val firebaseDb = Firebase.database
        val myRef = firebaseDb.getReference("message")
        myRef.setValue("Good work2")*/
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val mainViewModel: MainViewModel = viewModel()
    TaskManagerTheme {
        MainScreen(mainViewModel)
    }
}