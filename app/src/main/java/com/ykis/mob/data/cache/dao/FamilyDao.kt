package com.ykis.mob.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.mob.domain.family.FamilyEntity
import androidx.room.Query

@Dao
interface FamilyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFamily(appartment:List<FamilyEntity>)
    @Query("select * from family where address_id = :addressId")
    fun getFamilyByApartment(addressId:Int): List<FamilyEntity>
    @Query("delete from family")
    fun deleteAllFamily()
    @Query("delete from family where address_id not in (:addressIds)")
    fun deleteFamilyByApartment(addressIds: List<Int>)
}