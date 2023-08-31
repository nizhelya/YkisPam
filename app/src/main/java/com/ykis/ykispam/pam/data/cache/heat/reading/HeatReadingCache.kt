package com.ykis.ykispam.pam.data.cache.heat.reading

import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity

interface HeatReadingCache {
    fun insertHeatReading(heatReading: List<HeatReadingEntity>)
    fun getHeatReading(teplomerId: Int): List<HeatReadingEntity>
    fun deleteAllHeatReading()
    fun deleteHeatReadingFromFlat(addressId: List<Int>)
}