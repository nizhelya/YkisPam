package com.ykis.ykispam.data.cache.water.meter

import com.ykis.ykispam.domain.water.meter.WaterMeterEntity

interface WaterMeterCache {
    fun insertWaterMeter(waterMeters: List<WaterMeterEntity>)
    fun getWaterMeter(addressId: Int): List<WaterMeterEntity>
    fun deleteAllWaterMeter()
    fun deleteWaterMeterByApartment(addressIds:List<Int>)
}