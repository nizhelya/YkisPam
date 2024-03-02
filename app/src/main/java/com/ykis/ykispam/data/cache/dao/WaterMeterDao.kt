package com.ykis.ykispam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterEntity
import androidx.room.Query

@Dao
interface WaterMeterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWaterMeter(waterMeter: List<WaterMeterEntity>)

    @Query("select *   from water_meter where address_id = :addressId order by work")
    fun getWaterMeter(addressId: Int): List<WaterMeterEntity>

    @Query("delete from water_meter")
    fun deleteAllWaterMeter()
    @Query("delete from water_meter where address_id not in (:addressIds)")
    fun deleteWaterMeterByApartment(addressIds: List<Int>)

}