package com.ykis.ykispam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.ykispam.domain.apartment.ApartmentEntity

@Dao
interface ApartmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApartmentList(apartment:List<ApartmentEntity>)

    @Query("delete from apartment")
    fun deleteAllApartments()

    @Query("delete from apartment where address_id = :addressId")
    fun deleteFlat(addressId :Int)
    @Query("select * from apartment where address_id = :addressId")
    fun getFlatById(addressId :Int) : ApartmentEntity?


    @Query("select * from apartment")
    fun getApartmentList(): List<ApartmentEntity>


}