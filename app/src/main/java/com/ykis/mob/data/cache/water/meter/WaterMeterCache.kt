package com.ykis.mob.data.cache.water.meter

import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity

interface WaterMeterCache {
    fun insertWaterMeter(waterMeters: List<WaterMeterEntity>)
    fun getWaterMeter(addressId: Int): List<WaterMeterEntity>
    fun deleteAllWaterMeter()
    fun deleteWaterMeterByApartment(addressIds:List<Int>)
}