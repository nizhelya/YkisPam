package com.ykis.ykispam.ui.screens.meter.heat

import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity

data class HeatMeterState(
    val heatMeterList: List<HeatMeterEntity> = emptyList(),
    val selectedHeatMeter : HeatMeterEntity = HeatMeterEntity(),
    val heatReadings : List<HeatReadingEntity> = emptyList(),
    val lastHeatReading : HeatReadingEntity = HeatReadingEntity(),
    val isMetersLoading:Boolean = true,
    val isLastReadingLoading : Boolean = true,
    val isReadingsLoading:Boolean = true,
    val error : String = ""
)