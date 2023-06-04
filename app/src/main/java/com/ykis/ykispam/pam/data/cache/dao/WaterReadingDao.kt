package com.ykis.ykispam.pam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity
import androidx.room.Query

@Dao
interface WaterReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWaterReading(waterMeter: List<WaterReadingEntity>)

    @Query("select * from water_reading where vodomer_id = :vodomerId")
    fun getWaterReading(vodomerId: Int): List<WaterReadingEntity>

    @Query("delete from water_reading")
    fun deleteAllReadings()

    @Query("delete from water_reading where address_id not in (:addressId)")
    fun deleteReadingFromFlat(addressId: List<Int>)
}