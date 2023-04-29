package com.project.task.manager.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.task.manager.R
import com.project.task.manager.ui.theme.TaskManagerTheme
import com.project.task.manager.utils.Constants
import ir.kaaveh.sdpcompose.sdp


private const val TAG = "Component"

@Composable
fun TaskImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    description: String,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier
            .size(25.sdp)
            .clip(CircleShape),
        painter = painter,
        contentDescription = description,
        colorFilter = colorFilter

    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier,
    tittle: String,
    scrollState: TopAppBarScrollBehavior,
    popMenuState: MutableState<Boolean>,
    icon: @Composable () -> Unit
) {
    MediumTopAppBar(
        modifier = modifier, title = {
            Text(
                text = tittle,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = icon,
        colors = TopAppBarDefaults.mediumTopAppBarColors(),
        scrollBehavior = scrollState,
        actions = {
            IconButton(onClick = { popMenuState.value = !popMenuState.value }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_icon_cd)
                )
            }
            DropdownMenu(
                expanded = popMenuState.value,
                onDismissRequest = { popMenuState.value = !popMenuState.value }) {
                DropdownMenuItem(text = { Text(text = stringResource(R.string.grid)) },
                    onClick = {
                        popMenuState.value = !popMenuState.value
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_grid),
                            contentDescription = stringResource(id = R.string.grid_icon_cd)
                        )
                    }
                )
                DropdownMenuItem(text = { Text(text = stringResource(R.string.list)) }, onClick = {
                    popMenuState.value = !popMenuState.value
                },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_task_list),
                            contentDescription = stringResource(id = R.string.grid_icon_cd)
                        )
                    }
                )
            }
        }
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DefaultPreviewTaskImage() {
    TaskManagerTheme {
        val scrollState = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        val popMenuState = remember {
            mutableStateOf(false)
        }
        AppToolbar(
            modifier = Modifier,
            tittle = Constants.HOME_SCREEN,
            scrollState = scrollState,
            popMenuState = popMenuState
        ) {
        }
    }
}
