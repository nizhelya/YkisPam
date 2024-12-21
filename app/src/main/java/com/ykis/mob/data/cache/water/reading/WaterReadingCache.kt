package com.ykis.mob.data.cache.water.reading

import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity

interface WaterReadingCache {
    fun insertWaterReading(waterReading: List<WaterReadingEntity>)
    fun getWaterReading(vodomerId: Int): List<WaterReadingEntity>
    fun deleteAllWaterReading()
    fun deleteWaterReadingByApartment(addressIds:List<Int>)
}