package com.ykis.ykispam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import androidx.room.Query

@Dao
interface WaterReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWaterReading(waterMeter: List<WaterReadingEntity>)

    @Query("select * from water_reading where vodomer_id = :vodomerId")
    fun getWaterReading(vodomerId: Int): List<WaterReadingEntity>

    @Query("delete from water_reading")
    fun deleteAllWaterReadings()

    @Query("delete from water_reading where address_id not in (:addressId)")
    fun deleteWaterReadingFromFlat(addressId: List<Int>)
}