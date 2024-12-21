package com.ykis.mob.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity

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