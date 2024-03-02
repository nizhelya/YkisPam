package com.ykis.ykispam.data.cache.heat.meter

import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity

interface HeatMeterCache {
    fun insertHeatMeter(waterMeters: List<HeatMeterEntity>)
    fun getHeatMeter(addressId: Int): List<HeatMeterEntity>
    fun deleteAllHeatMeter()
    fun deleteHeatMeterByApartment(addressIds: List<Int>)
}