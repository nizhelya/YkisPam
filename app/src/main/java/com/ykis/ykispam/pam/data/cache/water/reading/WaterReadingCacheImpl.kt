package com.ykis.ykispam.pam.data.cache.water.reading

import com.ykis.ykispam.pam.data.cache.dao.WaterReadingDao
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterReadingCacheImpl @Inject constructor(
    private val waterReadingDao: WaterReadingDao
) : WaterReadingCache {
    override fun insertWaterReading(waterReading: List<WaterReadingEntity>) {
        waterReadingDao.insertWaterReading(waterReading)
    }

    override fun getWaterReading(vodomerId: Int): List<WaterReadingEntity> {
        return waterReadingDao.getWaterReading(vodomerId)
    }

    override fun deleteAllReading() {
        waterReadingDao.deleteAllReadings()
    }

    override fun deleteReadingFromFlat(addressId: List<Int>) {
        waterReadingDao.deleteReadingFromFlat(addressId)
    }
}