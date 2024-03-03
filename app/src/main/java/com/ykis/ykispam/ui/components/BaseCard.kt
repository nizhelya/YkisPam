package com.ykis.ykispam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun BaseCard(
    modifier: Modifier = Modifier.fillMaxWidth().padding(8.dp),
    label : String? = null,
    content :@Composable () ->Unit
) {
    Card(
        modifier= modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            if(label!=null){
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight(350)
                    )
                )
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
        ){
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
                    checked  = true
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