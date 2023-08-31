package com.ykis.ykispam.pam.data.cache.water.meter

import com.ykis.ykispam.pam.data.cache.dao.WaterMeterDao
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterMeterCacheImpl @Inject constructor(
    private val waterMeterDao: WaterMeterDao
) : WaterMeterCache {
    override fun insertWaterMeter(waterMeters: List<WaterMeterEntity>) {
        waterMeterDao.insertWaterMeter(waterMeters)
    }

    override fun getWaterMeter(addressId: Int): List<WaterMeterEntity> {
        return waterMeterDao.getWaterMeter(addressId)
    }

    override fun deleteAllWaterMeter() {
        waterMeterDao.deleteAllWaterMeter()
    }
}