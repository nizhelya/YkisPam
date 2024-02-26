package com.ykis.ykispam.ui.screens.meter.water

import com.ykis.ykispam.domain.water.meter.WaterMeterEntity

data class WaterMeterState(
    val waterMeterList: List<WaterMeterEntity> = emptyList(),
    val isLoading:Boolean = false,
    val error : String = ""
)