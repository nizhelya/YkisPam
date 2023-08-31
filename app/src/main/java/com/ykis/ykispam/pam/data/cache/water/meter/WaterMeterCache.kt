package com.ykis.ykispam.pam.data.cache.water.meter

import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity

interface WaterMeterCache {
    fun insertWaterMeter(waterMeters: List<WaterMeterEntity>)
    fun getWaterMeter(addressId: Int): List<WaterMeterEntity>
    fun deleteAllWaterMeter()
}