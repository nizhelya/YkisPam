package com.ykis.ykispam.data.cache.heat.reading

import com.ykis.ykispam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeatReadingCacheImpl @Inject constructor(
    private val heatReadingDao: HeatReadingDao
) : HeatReadingCache {
    override fun insertHeatReading(heatReading: List<HeatReadingEntity>) {
        heatReadingDao.insertHeatReading(heatReading)
    }

    override fun getHeatReading(teplomerId: Int): List<HeatReadingEntity> {
        return heatReadingDao.getHeatReading(teplomerId)
    }

    override fun deleteAllHeatReading() {
        heatReadingDao.deleteAllHeatReadings()
    }

    override fun deleteHeatReadingFromFlat(addressId: List<Int>) {
        heatReadingDao.deleteHeatReadingFromFlat(addressId)
    }


}