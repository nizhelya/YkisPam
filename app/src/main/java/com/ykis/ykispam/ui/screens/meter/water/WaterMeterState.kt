package com.ykis.ykispam.ui.screens.meter.water

import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity

data class WaterMeterState(
    val waterMeterList: List<WaterMeterEntity> = emptyList(),
    val selectedWaterMeter : WaterMeterEntity = WaterMeterEntity(),
    val waterReadings: List<WaterReadingEntity> = emptyList(),
    val lastReading : WaterReadingEntity = WaterReadingEntity(),
    val isMetersLoading:Boolean = false,
    val isReadingsLoading:Boolean = false,
    val error : String = ""
)