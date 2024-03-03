package com.ykis.ykispam.ui.screens.meter.heat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithCheckBox
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.screens.meter.AddReadingDialog
import com.ykis.ykispam.ui.screens.meter.LastReadingCardButtons
import com.ykis.ykispam.ui.screens.meter.heat.reading.HeatReadingItemContent
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun HeatMeterDetail(
    modifier: Modifier = Modifier,
    heatMeterEntity: HeatMeterEntity,
    baseUIState: BaseUIState,
    getLastHeatReading:()->Unit,
    lastHeatReading: HeatReadingEntity,
    navigateToReadings: () -> Unit,
    isWorking : Boolean
) {
    var showAddReadingDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = baseUIState.addressId , key2 = heatMeterEntity.teplomerId ) {
        if(isWorking){
            getLastHeatReading()
        }
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        if(isWorking){
            BaseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(CardDefaults.shape)
                    .clickable {
                        navigateToReadings()
                    },
                label = stringResource(id = R.string.last_reading)
            ) {
                HeatReadingItemContent(
                    reading = lastHeatReading,
                    isAverage = lastHeatReading.avg.isTrue()
                )
            }
            LastReadingCardButtons(
                onAddButtonClick = {
                                   showAddReadingDialog=true
                },
                onDeleteButtonClick = {}
            )
        }
        BaseCard(label = stringResource(id = R.string.meter_detail_text) ) {
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
        if(isWorking){
            BaseCard(label = stringResource(id = R.string.check_water_meter)) {
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
    if (showAddReadingDialog) {
        AddReadingDialog(
            onDismissRequest = {
                showAddReadingDialog = false
//                onNewReadingChange("")
            },
            onAddClick = {},
            currentReading = lastHeatReading.currant.toString(),
            newReading = "",
            onReadingChange = {}
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_6")
@Composable
private fun PreviewHeatMeterDetail() {
    YkisPAMTheme {
        HeatMeterDetail(
            heatMeterEntity = HeatMeterEntity(
            ),
            baseUIState = BaseUIState(),
            getLastHeatReading = {},
            lastHeatReading = HeatReadingEntity(
                avg = 1
            ),
            isWorking = true,
            navigateToReadings = {}
        )
    }
}