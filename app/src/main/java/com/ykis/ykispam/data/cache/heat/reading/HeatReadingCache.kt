package com.ykis.ykispam.data.cache.heat.reading

import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity

interface HeatReadingCache {
    fun insertHeatReading(heatReading: List<HeatReadingEntity>)
    fun getHeatReading(teplomerId: Int): List<HeatReadingEntity>
    fun deleteAllHeatReading()
    fun deleteHeatReadingsByApartment(addressIds:List<Int>)
}