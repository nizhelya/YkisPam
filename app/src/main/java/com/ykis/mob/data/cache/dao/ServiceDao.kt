package com.ykis.mob.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.mob.domain.service.ServiceEntity

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(service: List<ServiceEntity>)

    @Query("select * from service where address_id = :addressId and service = :service and strftime('%Y', data) = :year order by data DESC")
    fun getServiceDetail(addressId: Int, service: String , year:String): List<ServiceEntity>

    @Query("delete from service")
    fun deleteAllService()

    @Query("select *  from service where address_id = :addressId and service = 'total'")
    fun getTotalDebt(addressId: Int): ServiceEntity?

    @Query("delete from service where address_id not in (:addressIds)")
    fun deleteServiceByApartment(addressIds: List<Int>)


}