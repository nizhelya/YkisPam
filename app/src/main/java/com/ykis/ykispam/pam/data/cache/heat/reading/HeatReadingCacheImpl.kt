package com.ykis.ykispam.pam.data.cache.heat.reading

import com.ykis.ykispam.pam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
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

    override fun deleteAllReading() {
        heatReadingDao.deleteAllReadings()
    }

    override fun deleteReadingFromFlat(addressId: List<Int>) {
        heatReadingDao.deleteReadingFromFlat(addressId)
    }


}