package com.ykis.ykispam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity


@Dao
interface HeatReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeatReading(heatMeter:List<HeatReadingEntity>)
    @Query("select * from heat_reading where teplomer_id = :teplomerId")
    fun getHeatReading(teplomerId:Int): List<HeatReadingEntity>
    @Query("delete from heat_reading")
    fun deleteAllHeatReadings()

    @Query("delete from heat_reading where address_id not in (:addressIds)")
    fun deleteHeatReadingsByApartment(addressIds: List<Int>)

    @Query("delete from heat_reading where pok_id = :readingId")
    fun deleteHeatReadingById(readingId:Int)
}
