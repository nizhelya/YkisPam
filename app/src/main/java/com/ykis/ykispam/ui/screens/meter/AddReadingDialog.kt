package com.ykis.ykispam.ui.screens.meter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.NumberField

@Composable
fun AddReadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onAddClick: () -> Unit,
    currentReading: String,
    newReading:String,
    onReadingChange: (String) -> Unit,
    enabledButton:Boolean,
    isInteger: Boolean
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp)),            modifier = modifier.padding(all = 4.dp)
        ) {
            Column(
                modifier = modifier.padding(all = 24.dp)
            ) {

                Text(
                    text = stringResource(R.string.add_reading_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text= stringResource(R.string.add_reading_supporting_text)
                )
                OutlinedTextField(
                    modifier = modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(id = R.string.current_reading)
                        )
                    },
                    readOnly = true,
                    value = currentReading,
                    onValueChange = {}
                )
                NumberField(
                    value = newReading,
                    onNewValue = onReadingChange,
                    label = R.string.new_reading,
                    isInteger = isInteger
                )


                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onDismissRequest()
                    }) {
                        Text(
                            text = stringResource(id = R.string.cancel)
                        )
                    }
                    TextButton(
                        onClick = {
                            onDismissRequest()
                            onAddClick()
                        },
                        enabled = enabledButton
                    ) {
                        Text(
                            stringResource(R.string.add)
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun PreviewDialog() {
    AddReadingDialog(
        onDismissRequest = { },
        onAddClick = { },
        currentReading = 940.toString(),
        newReading = "",
        onReadingChange = {},
        enabledButton = false,
        isInteger = true
    )
}