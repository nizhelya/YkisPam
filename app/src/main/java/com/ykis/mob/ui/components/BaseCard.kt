package com.ykis.mob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.mob.R
import com.ykis.mob.ui.theme.YkisPAMTheme

@Composable
fun BaseCard(
    cardModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 12.dp),
    columnModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
    labelModifier: Modifier = Modifier,
    label: String? = null,
    actionButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = cardModifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (label != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = labelModifier
                            .weight(1f)
                            .padding(bottom = 4.dp),
                        text = label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    if (actionButton != null) {
                        actionButton()
                    }
                }
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBaseCard() {
    YkisPAMTheme {
        BaseCard(
            label = stringResource(id = R.string.last_reading)
        ) {
            LabelTextWithText(
                labelText = stringResource(id = R.string.model_colon),
                valueText = "0"
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.number_colon),
                valueText = "0"
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.place_colon),
                valueText = "0"
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.position_colon),
                valueText = "0"
            )
            LabelTextWithCheckBox(
                labelText = stringResource(id = R.string.stoki_colon),
                checked = true
            )
            LabelTextWithCheckBox(
                labelText = stringResource(id = R.string.general_colon),
                checked = true
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.zdate_colon),
                valueText = "0"
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.sdate_colon),
                valueText = "0"
            )
        }
    }
}