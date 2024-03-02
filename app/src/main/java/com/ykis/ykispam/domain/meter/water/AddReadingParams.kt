package com.ykis.ykispam.domain.meter.water

data class AddReadingParams(
    val uid : String,
    val meterId: Int,
    val newValue: Int,
    val currentValue: Int
)