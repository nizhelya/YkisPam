package com.ykis.ykispam.ui.screens.meter.heat

import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity

data class HeatMeterState(
    val heatMeterList: List<HeatMeterEntity> = emptyList(),
    val selectedHeatMeter : HeatMeterEntity = HeatMeterEntity(),
    val heatReadings : List<HeatReadingEntity> = emptyList(),
    val isLoading:Boolean = false,
    val isReadingsLoading:Boolean = false,
    val error : String = ""
)