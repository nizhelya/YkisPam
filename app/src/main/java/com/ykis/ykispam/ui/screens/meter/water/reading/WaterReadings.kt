package com.ykis.ykispam.ui.screens.meter.water.reading

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ykis.ykispam.core.CenteredProgressIndicator
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState

@Composable
fun WaterReadings(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    waterMeterState: WaterMeterState,
    getWaterReadings: () ->Unit,
) {
    LaunchedEffect(key1 = baseUIState.addressId , waterMeterState.selectedWaterMeter) {
        getWaterReadings()
    }
        Crossfade(
            targetState = waterMeterState.isReadingsLoading,
            label = "",
            animationSpec = tween(delayMillis = 500)
        ) {
        isLoading->
        if(isLoading){
            CenteredProgressIndicator()
        }else  LazyColumn {
            items(
                waterMeterState.waterReadings
            ){
                    waterReading->
                    WaterReadingItem(reading = waterReading)
            }
        }

    }

}

