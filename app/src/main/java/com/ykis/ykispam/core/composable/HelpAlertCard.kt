package com.ykis.ykispam.core.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R.string as AppText

@Composable
fun HelpAlertCard(
    title: String,
    text: String,
    org: String,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit
) {
    var showWarningDialog by rememberSaveable { mutableStateOf(showDialog) }

    if (showWarningDialog) {
        AlertDialog(
            icon = { Icon(imageVector = Icons.TwoTone.Info, contentDescription = "Info") },
            title = { Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                        vertical = 4.dp
                    ),
            ) },
            text = {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 4.dp,
                            vertical = 4.dp
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = AppText.distributor),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = org,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            },

            dismissButton = {
                DialogCancelButton(text = AppText.ok) {
                    showWarningDialog = false
                    onShowDialogChange(showWarningDialog)
                }
            },
            confirmButton = { },
            onDismissRequest = {
                showWarningDialog = false
                onShowDialogChange(showWarningDialog)
            }

        )
    }
}