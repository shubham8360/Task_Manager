package com.project.task.manager.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.task.manager.R
import com.project.task.manager.navigation.BottomBarScreens
import com.project.task.manager.navigation.BottomNavGraph
import com.project.task.manager.ui.components.AppToolbar
import com.project.task.manager.utils.Constants
import com.project.task.manager.vm.MainViewModel

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val topBarTittleState = viewModel.screen.collectAsState()
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(topBar = {
        AppToolbar(modifier = Modifier, tittle = topBarTittleState.value, scrollState) {
            IconButton(onClick = {
                Toast.makeText(context, "Under maintenance", Toast.LENGTH_SHORT).show()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = stringResource(R.string.back_button_cd))
            }
        }

    }, bottomBar = {
        BottomBarScreen(navHostController = navController)
    }, floatingActionButton = {
        if (topBarTittleState.value == Constants.HOME_SCREEN) {
            FloatingActionButton(onClick = { viewModel.inputDialogState.value = !viewModel.inputDialogState.value }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task_cd))
            }
        }
    }
    ) {
        BottomNavGraph(modifier = Modifier.padding(it), navHostController = navController)
    }
}

@Preview
@Composable
fun BottomBarScreen(navHostController: NavHostController = rememberNavController()) {
    val screens = listOf(
        BottomBarScreens.TaskScreen,
        BottomBarScreens.CompletedTaskScreen,
        BottomBarScreens.CalenderScreen
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
    screen: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, label = {
        Text(text = screen.tittle)
    }, icon = {
        Icon(painter = painterResource(id = screen.icon), contentDescription = "")
    },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })

}
