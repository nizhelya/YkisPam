package com.ykis.mob.ui.screens.chat

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteMessageDialog(
    modifier: Modifier = Modifier,
    onDismiss:()-> Unit,
    onConfirm : () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
                        TextButton(onClick = { onConfirm() }) {
                            Text(
                                "Видалити"
                            )
                        }
         },
        dismissButton = {
            TextButton(onClick = { onDismiss()}) {
                Text(
                    "Відмінити"
                )
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        },
        title = {
            Text(
                "Видалити повідомлення?"
            )
        },
        text = {
            Text(
                "Повідомлення буде видалено безповоротно!"
            )
        },
    )
}