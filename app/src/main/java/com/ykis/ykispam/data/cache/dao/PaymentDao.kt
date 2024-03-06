package com.ykis.ykispam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.ykispam.domain.payment.PaymentEntity

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(payments:List<PaymentEntity>)
    @Query("select * from payment where address_id = :addressId")
    fun getPaymentFromFlat(addressId:Int): List<PaymentEntity>
    @Query("delete from payment")
    fun deleteAllPayment()
//    @Query("select year  from payment where address_id= :addressId group by year order by year DESC")
//    fun getYearsByFlat(addressId: Int):List<Int>
//    @Query("select * from payment where address_id = :addressId and year = :year ORDER BY data DESC")
//    fun getPaymentsFromYearsFlat(addressId: Int , year: Int):List<PaymentEntity>
    @Query("delete from payment where address_id not in (:addressIds)")
    fun deletePaymentByApartment(addressIds: List<Int>)
}