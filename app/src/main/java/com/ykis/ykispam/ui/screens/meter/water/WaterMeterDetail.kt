package com.ykis.ykispam.ui.screens.meter.water

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.LabelTextWithCheckBox
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.screens.meter.LastReadingCard
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.customTitleForCard

@Composable
fun WaterMeterDetail(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    waterMeterEntity: WaterMeterEntity,
    lastReading: WaterReadingEntity,
    getLastReading:()->Unit
) {
    LaunchedEffect(key1 = baseUIState.addressId, key2 = waterMeterEntity.vodomerId) {
        getLastReading()
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp)
    ) {
        LastReadingCard(
            lastReading =lastReading
        )
        HorizontalDivider(
            modifier = modifier.padding(vertical = 4.dp)
        )
        Text(
            text = stringResource(id = R.string.water_detail_text),
            style = customTitleForCard
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LabelTextWithText(
                    labelText = stringResource(id = R.string.model_colon),
                    valueText = waterMeterEntity.model
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.number_colon),
                    valueText = waterMeterEntity.nomer
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.place_colon),
                    valueText = waterMeterEntity.place
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.position_colon),
                    valueText = waterMeterEntity.position
                )
                LabelTextWithCheckBox(
                    labelText = stringResource(id = R.string.stoki_colon),
                    checked = waterMeterEntity.st.isTrue()
                )
                LabelTextWithCheckBox(
                    labelText = stringResource(id = R.string.general_colon),
                    checked = waterMeterEntity.avg.isTrue()
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.zdate_colon),
                    valueText = waterMeterEntity.zdate
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.sdate_colon),
                    valueText = waterMeterEntity.sdate
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
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
            LabelTextWithText(
                labelText = stringResource(id = R.string.pdate_colon),
                valueText = waterMeterEntity.pdate
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.fdate_colon),
                valueText = waterMeterEntity.fpdate
            )
            LabelTextWithCheckBox(
                labelText = stringResource(id = R.string.stop_colon),
                checked = waterMeterEntity.spisan.isTrue()
            )
        }

            }
    }
}

@Preview(showBackground = true, device = "id:pixel_6")
@Composable
private fun PreviewWaterMeterDetail() {
    YkisPAMTheme {
        WaterMeterDetail(
            baseUIState = BaseUIState(),
            waterMeterEntity = WaterMeterEntity(
                model = "GLS 3 ULTRA"
            ),
            lastReading = WaterReadingEntity(),
            getLastReading = {}
        )
    }
}