package com.ykis.ykispam.pam.screens.add_appartment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddAppartmentScreen(
    openScreen:  (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddAppartmentViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    )
    {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//            Spacer(modifier = Modifier.smallSpacer())
    }
}

//        val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
//        val options by viewModel.options
//
//        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//            ActionToolbar(
//                title = AppText.tasks,
//                modifier = Modifier.toolbarActions(),
//                endActionIcon = AppIcon.ic_admin_settings_24,
//                endAction = { viewModel.onSettingsClick(openScreen) }
//            )
//
//            Spacer(modifier = Modifier.smallSpacer())
//
//            LazyColumn {
//                items(tasks.value, key = { it.id }) { taskItem ->
//                    TaskItem(
//                        task = taskItem,
//                        options = options,
//                        onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
//                        onActionClick = { action -> viewModel.onTaskActionClick(openScreen, taskItem, action) }
//                    )
//                }
//            }
        }

//
//    LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
