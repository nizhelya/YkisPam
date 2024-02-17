package com.ykis.ykispam.ui.screens.bti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.glance.Button
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.EmailField
import com.ykis.ykispam.core.composable.PhoneField
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun ContactsCard(
    onUpdateBti : () -> Unit,
    modifier : Modifier = Modifier,
    phone : String,
    email : String,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.contacts),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),

                    )
                IconButton(onClick = {
                    openDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit ,
                        contentDescription = "edit"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                       all = 4.dp
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                        ),
                    text = stringResource(R.string.phone_colon),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = phone,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Row(
                modifier = Modifier
                    .padding(
                        all = 4.dp
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                        ),
                    text = stringResource(R.string.email_colon),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
    if(openDialog.value){
        ChangeContactsDialog(
            onDismissRequest = { openDialog.value = false},
            phone = phone,
            email = email ,
            onEmailChange = onEmailChange,
            onPhoneChange = onPhoneChange,
            onUpdateClick = onUpdateBti
        )
    }
}

@Composable
fun ChangeContactsDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onUpdateClick : () -> Unit,
    phone : String,
    email : String,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest()}) {
        Card {
            Column(
                modifier = modifier.padding(all = 24.dp)
            ){
                PhoneField(value =phone , onNewValue =onPhoneChange )
                EmailField(value = email, onNewValue =onEmailChange )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(
                            text = "Відмінити"
                        )
                    }
                    TextButton(onClick = {
                        onDismissRequest()
                        onUpdateClick()
                    }) {
                        Text(
                            "Оновити"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewContactsCard() {
    ContactsCard(phone = "+3806337442",
        email = "rshulik74@gmail.com",
        onEmailChange = {} ,
        onPhoneChange = {},
        onUpdateBti = {}
    )
}