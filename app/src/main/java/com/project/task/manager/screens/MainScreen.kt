package com.project.task.manager.screens

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.task.manager.R
import com.project.task.manager.navigation.BottomBarScreen
import com.project.task.manager.navigation.BottomNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomBarScreen(navHostController = navController)
    }) {
        Log.d("TAG", "MainScreen: $it")
        BottomNavGraph(navHostController = navController)
    }
}

@Composable
fun BottomBarScreen(navHostController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.TaskScreen,
        BottomBarScreen.CalenderScreen,
        BottomBarScreen.CompletedTaskScreen
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navHostController)
        }
    }


}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, label = {
        Text(text = screen.tittle)
    }, icon = {
        Icon(painter = painterResource(id = screen.icon) , contentDescription = "")
    },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })

}
