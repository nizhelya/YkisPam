package com.ykis.ykispam.data.cache.water.reading

import com.ykis.ykispam.domain.water.reading.WaterReadingEntity

interface WaterReadingCache {
    fun insertWaterReading(waterReading: List<WaterReadingEntity>)
    fun getWaterReading(vodomerId: Int): List<WaterReadingEntity>
    fun deleteAllWaterReading()
    fun deleteWaterReadingByApartment(addressIds:List<Int>)
}