package com.ykis.ykispam.ui.screens.meter.water

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.screens.meter.MeterViewModel

@Composable
fun WaterMeterList(
    modifier: Modifier = Modifier,
    viewModel : MeterViewModel,
    baseUIState: BaseUIState
) {
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = baseUIState.addressId) {
        viewModel.getWaterMeterList(baseUIState.uid!!,baseUIState.addressId)
    }
    LazyColumn {
        items(
            waterMeterState.waterMeterList
        ) { waterMeter ->
            WaterMeterItem(waterMeter = waterMeter)
        }
    }
}