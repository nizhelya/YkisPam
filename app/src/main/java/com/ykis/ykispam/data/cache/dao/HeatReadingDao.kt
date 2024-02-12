package com.ykis.ykispam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity
import androidx.room.Query


@Dao
interface HeatReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeatReading(heatMeter:List<HeatReadingEntity>)
    @Query("select * from heat_reading where teplomer_id = :teplomerId")
    fun getHeatReading(teplomerId:Int): List<HeatReadingEntity>
    @Query("delete from heat_reading")
    fun deleteAllHeatReadings()

    @Query("delete from heat_reading where address_id not in (:addressId)")
    fun deleteHeatReadingFromFlat(addressId: List<Int>)
}
