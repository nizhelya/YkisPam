package com.ykis.ykispam.pam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import androidx.room.Query

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(service: List<ServiceEntity>)

    @Query("select * from service where address_id = :addressId and service = :service order by data DESC")
    fun getServiceFromFlat(addressId: Int, service: String): List<ServiceEntity>

    @Query("delete from service")
    fun deleteAllService()

    @Query("select *  from service where address_id = :addressId and service = 'total'")
    fun getTotalDebt(addressId: Int): ServiceEntity?
}