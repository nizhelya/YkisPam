package com.ykis.ykispam.ui.screens.meter.water

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithCheckBox
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.screens.meter.AddReadingDialog
import com.ykis.ykispam.ui.screens.meter.DeleteReadingDialog
import com.ykis.ykispam.ui.screens.meter.LastReadingCardButtons
import com.ykis.ykispam.ui.screens.meter.water.reading.WaterReadingItemContent
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun WaterMeterDetail(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    waterMeterEntity: WaterMeterEntity,
    lastReading: WaterReadingEntity,
    getLastReading: () -> Unit,
    onNewReadingChange: (String) -> Unit,
    newWaterReading: String,
    addReading: () -> Unit,
    deleteReading: () -> Unit,
    isWorking: Boolean,
    navigateToReadings: () -> Unit,
    isLastReadingLoading: Boolean
) {
    var showAddReadingDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showDeleteReadingDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val enabledButton by remember(key1 =newWaterReading ,key2 = lastReading.current  ) {
        derivedStateOf {
            (newWaterReading.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: -1) > lastReading.current
        }
    }
    LaunchedEffect(key1 = baseUIState.addressId, key2 = waterMeterEntity.vodomerId) {
        if (isWorking) {
            getLastReading()
        }
    }
    Crossfade(
        targetState = isLastReadingLoading,
        label = "",
        animationSpec = tween(delayMillis = 500)
    ) { isLoading ->
        if (isLoading) {
            ProgressBar()
        } else Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            if (isWorking) {
                BaseCard(
                    label = stringResource(id = R.string.last_reading),
                    cardModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(CardDefaults.shape)
                        .clickable {
                            navigateToReadings()
                        }) {
                    WaterReadingItemContent(
                        reading = lastReading
                    )
                }
                LastReadingCardButtons(
                    onAddButtonClick = { showAddReadingDialog = true },
                    onDeleteButtonClick = { showDeleteReadingDialog = true },
                    showDeleteButton = (lastReading.dateDo == SimpleDateFormat("dd.MM.yyyy").format(
                        Date()
                    ))
                )
            }
            BaseCard(label = stringResource(id = R.string.meter_detail_text)) {
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
                if(waterMeterEntity.spisan.isTrue()){
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.date_spisan_colon),
                        valueText = waterMeterEntity.dataSpis
                    )
                }
            }
            if (isWorking) {
                BaseCard(label = stringResource(id = R.string.check_water_meter)) {
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
    if (showAddReadingDialog) {
        AddReadingDialog(
            onDismissRequest = {
                showAddReadingDialog = false
                onNewReadingChange("")
            },
            onAddClick = addReading,
            currentReading = lastReading.current.toString(),
            newReading = newWaterReading,
            onReadingChange = onNewReadingChange,
            enabledButton = enabledButton,
            typeNumber = true
        )
    }
    if (showDeleteReadingDialog) {
        DeleteReadingDialog(
            onDismissRequest = { showDeleteReadingDialog = false },
            onDeleteClick = { deleteReading() }
        )
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
            lastReading = WaterReadingEntity(
                avg = 1
            ),
            getLastReading = {},
            isWorking = true,
            isLastReadingLoading = false,
            onNewReadingChange = {},
            newWaterReading = "",
            addReading = {},
            deleteReading = {},
            navigateToReadings = {}
        )
    }
}