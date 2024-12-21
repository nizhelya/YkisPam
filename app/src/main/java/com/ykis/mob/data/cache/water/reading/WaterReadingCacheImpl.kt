package com.ykis.mob.data.cache.water.reading

import com.ykis.mob.data.cache.dao.WaterReadingDao
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity
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
        return waterReadingDao.getWaterReadings(vodomerId)
    }

    override fun deleteAllWaterReading() {
        waterReadingDao.deleteAllWaterReadings()
    }

    override fun deleteWaterReadingByApartment(addressIds: List<Int>) {
        waterReadingDao.deleteWaterReadingByApartment(addressIds)
    }

}