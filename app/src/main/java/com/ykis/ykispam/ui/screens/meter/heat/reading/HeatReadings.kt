package com.ykis.ykispam.ui.screens.meter.heat.reading

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ykis.ykispam.core.CenteredProgressIndicator
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState

@Composable
fun HeatReadings(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    heatMeterState: HeatMeterState,
    getHeatReadings: () -> Unit
) {
    LaunchedEffect(key1 = baseUIState.addressId, key2 = heatMeterState.selectedHeatMeter) {
        getHeatReadings()
    }
    Crossfade(
        targetState = heatMeterState.isReadingsLoading,
        label = "",
        animationSpec = tween(delayMillis = 300)
    ) { isLoading ->
        if (isLoading) {
            CenteredProgressIndicator()
        } else
            LazyColumn {
                items(
                    heatMeterState.heatReadings
                ) { heatReading ->
                    HeatReadingItem(
                        reading = heatReading,
                        isAverage = heatReading.avg.isTrue()
                    )
                }
            }

    }
}