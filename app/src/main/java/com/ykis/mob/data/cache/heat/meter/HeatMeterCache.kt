package com.ykis.mob.data.cache.heat.meter

import com.ykis.mob.domain.meter.heat.meter.HeatMeterEntity

interface HeatMeterCache {
    fun insertHeatMeter(waterMeters: List<HeatMeterEntity>)
    fun getHeatMeter(addressId: Int): List<HeatMeterEntity>
    fun deleteAllHeatMeter()
    fun deleteHeatMeterByApartment(addressIds: List<Int>)
}