package com.project.task.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.project.task.manager.db.TaskDatabase
import com.project.task.manager.repository.Repository
import com.project.task.manager.screens.MainScreen
import com.project.task.manager.ui.theme.TaskManagerTheme
import com.project.task.manager.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.kaaveh.sdpcompose.ssp
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var roomDatabase: TaskDatabase

    @Inject
    lateinit var imp: Repository

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TaskManagerTheme {
                MainScreen()
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = name, fontSize = 20.ssp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagerTheme {
        MainScreen()
    }
}