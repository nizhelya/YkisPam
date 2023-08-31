package com.ykis.ykispam.pam.data.cache.heat.meter

import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity

interface HeatMeterCache {
    fun insertHeatMeter(waterMeters: List<HeatMeterEntity>)
    fun getHeatMeter(addressId: Int): List<HeatMeterEntity>
    fun deleteAllHeatMeter()
}