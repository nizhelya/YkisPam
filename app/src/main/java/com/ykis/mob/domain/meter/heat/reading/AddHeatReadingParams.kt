package com.ykis.mob.domain.meter.heat.reading

data class AddHeatReadingParams(
    val meterId: Int,
    val newValue: Double,
    val currentValue: Double,
    val uid : String
)