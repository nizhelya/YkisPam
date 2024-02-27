package com.ykis.ykispam.ui.screens.meter.heat

import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity

data class HeatMeterState(
    val heatMeterList: List<HeatMeterEntity> = emptyList(),
    val selectedHeatMeter : HeatMeterEntity = HeatMeterEntity(),
    val isLoading:Boolean = false,
    val error : String = ""
)