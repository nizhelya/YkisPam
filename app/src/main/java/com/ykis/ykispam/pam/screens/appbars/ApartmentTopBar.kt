package com.ykis.ykispam.pam.screens.appbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceTheme.colors
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.core.composable.DangerousCardEditor
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.core.composable.RegularCardEditor
import com.ykis.ykispam.core.ext.card
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.screens.appartment.DeleteApartment
import kotlinx.coroutines.launch
import com.ykis.ykispam.R.string as AppText

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApartmentTopAppBar(
    contentType: ContentType,
    appState: YkisPamAppState,
    apartment: ApartmentEntity,
    isDriverClicked: Boolean,
    isButtonAction: Boolean,
    modifier: Modifier = Modifier,
    onButtonAction: () -> Unit,
    onButtonPressed: () -> Unit,


    ) {
    var openMenu by remember { mutableStateOf(false) }
    var showWarningDialog by remember { mutableStateOf(false) }
    if (apartment.address.isNotEmpty()) {

    }

    if (showWarningDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//            textContentColor =  MaterialTheme.colorScheme.onTertiaryContainer,
            title = { Text(stringResource(R.string.title_delete_appartment)) },
//            text = { Text(stringResource(R.string.title_delete_appartment)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.title_delete_appartment) {
                    onButtonAction()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
    if (contentType == ContentType.SINGLE_PANE) {

        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onSecondary

            ),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (apartment.address.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.add_appartment),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    } else {
                        Text(
                            text = apartment.address,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = stringResource(
                                id = R.string.response_address_id,
                                apartment.addressId.toString()
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            navigationIcon = {
                if (isDriverClicked) {
                    FilledIconButton(
                        onClick = {
                            appState.coroutineScope.launch {
                                // открывает ящик
                                onButtonPressed()
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.driver_menu),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                } else {
                    FilledIconButton(
                        onClick = onButtonPressed,
                        modifier = Modifier.padding(8.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

            },
            actions = {
                if (isButtonAction) {
                    IconButton(
                        onClick = {
                            appState.coroutineScope.launch {
                                // открывает ящик
                                showWarningDialog = true

                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.more_options_button),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    DropdownMenu(
                        expanded = openMenu,
                        onDismissRequest = { openMenu = !openMenu },
//                    modifier = Modifier
//                        .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        DeleteApartment { onButtonAction() }
                    }

                }
            }

        )
    } else {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (apartment.address.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.add_appartment),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    } else {
                        Text(
                            text = apartment.address,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = stringResource(
                                id = R.string.response_address_id,
                                apartment.addressId.toString()
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            actions = {
                if (isButtonAction) {
                    IconButton(
                        onClick = {
                            appState.coroutineScope.launch {
                                // открывает ящик
                                showWarningDialog = true

                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.more_options_button),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    DropdownMenu(
                        expanded = openMenu,
                        onDismissRequest = { openMenu = !openMenu },
//                    modifier = Modifier
//                        .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        DeleteApartment { onButtonAction() }
                    }
                }

            }
        )
    }
}
