package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.DangerousCardEditor
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.core.composable.RegularCardEditor
import com.ykis.ykispam.core.ext.card
import com.ykis.ykispam.R.string as AppText

@Composable
fun AppartmentTopBar(
    exitApp: () -> Unit,
    openScreen: () -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 12.dp,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(AppText.list_appartment)
                )
            }

        },
        actions = {
            IconButton(onClick = { openMenu = !openMenu })
            {
                Icon(imageVector = Icons.Outlined.Apps, contentDescription = null)
            }
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = { openMenu = !openMenu },
                modifier = Modifier
                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(20))
                    .background(color = MaterialTheme.colors.background)

            ) {
                ExitApp(exitApp)
                OpenSettingsScreen(openScreen)

            }

        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExitApp(exitApp: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(AppText.sign_out, R.drawable.ic_exit, "", Modifier.card()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    exitApp()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OpenSettingsScreen(openSettingsScreen: () -> Unit) {

    DangerousCardEditor(
        AppText.settings,
        R.drawable.ic_settings,
        "",
        Modifier.card()
    ) {
        openSettingsScreen()
    }


}