package com.ykis.mob.domain.meter.water.reading

data class AddWaterReadingParams(
    val uid : String,
    val meterId: Int,
    val newValue: Int,
    val currentValue: Int
)