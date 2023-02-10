package com.project.task.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.project.task.manager.screens.MainScreen
import com.project.task.manager.ui.theme.TaskManagerTheme
import ir.kaaveh.sdpcompose.ssp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                // A surface container using the 'background' color from the theme
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