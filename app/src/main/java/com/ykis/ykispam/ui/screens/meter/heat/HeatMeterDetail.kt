package com.ykis.ykispam.ui.screens.meter.heat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.ui.components.LabelTextWithCheckBox
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.screens.meter.LastReadingCard
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.customTitleForCard

@Composable
fun HeatMeterDetail(
    modifier: Modifier = Modifier,
    heatMeterEntity: HeatMeterEntity
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        LastReadingCard()
        HorizontalDivider(
            modifier = modifier.padding(vertical = 4.dp)
        )
            Text(
                text = stringResource(id = R.string.water_detail_text),
                style = customTitleForCard
            )
        Card(
            modifier = modifier.padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LabelTextWithText(
                    labelText = stringResource(id = R.string.model_colon),
                    valueText = heatMeterEntity.model
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.number_colon),
                    valueText = heatMeterEntity.number
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.edizm_colon),
                    valueText = heatMeterEntity.edizm
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.koef_colon),
                    valueText = heatMeterEntity.koef
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.area_colon),
                    valueText = heatMeterEntity.area.toString()
                )
                LabelTextWithCheckBox(
                    labelText = stringResource(id = R.string.out_colon),
                    checked = heatMeterEntity.out.isTrue()
                )
                LabelTextWithCheckBox(
                    labelText = stringResource(id = R.string.spisan_colon),
                    checked = heatMeterEntity.spisan.isTrue()
                )
            }
        }
        HorizontalDivider(
            modifier = modifier.padding(vertical = 4.dp)
        )
        Text(
            text = stringResource(id = R.string.check_water_meter),
            style = customTitleForCard
        )
        Card(
            modifier = modifier.padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LabelTextWithText(
                    labelText = stringResource(id = R.string.pdate_colon),
                    valueText = heatMeterEntity.pdate
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.fdate_colon),
                    valueText = heatMeterEntity.fpdate
                )
                LabelTextWithCheckBox(
                    labelText = stringResource(id = R.string.stop_colon),
                    checked = heatMeterEntity.spisan.isTrue()
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6")
@Composable
private fun PreviewHeatMeterDetail() {
    YkisPAMTheme {
        HeatMeterDetail(
            heatMeterEntity = HeatMeterEntity()
        )
    }
}