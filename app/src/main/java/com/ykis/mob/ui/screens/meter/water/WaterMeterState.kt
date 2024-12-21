package com.ykis.mob.ui.screens.meter.water

import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity

data class WaterMeterState(
    val waterMeterList: List<WaterMeterEntity> = emptyList(),
    val selectedWaterMeter : WaterMeterEntity = WaterMeterEntity(),
    val waterReadings: List<WaterReadingEntity> = emptyList(),
    val lastWaterReading : WaterReadingEntity = WaterReadingEntity(),
    val isMetersLoading:Boolean =true,
    val isLastReadingLoading : Boolean = false,
    val isReadingsLoading:Boolean = true,
    val newWaterReading : String = "" ,
    val error : String = ""
)