package com.ykis.ykispam.pam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import androidx.room.Query

@Dao
interface FamilyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFamily(appartment:List<FamilyEntity>)
    @Query("select * from family where address_id = :addressId")
    fun getFamilyFromFlat(addressId:Int): List<FamilyEntity>
    @Query("delete from family")
    fun deleteAllFamily()
}