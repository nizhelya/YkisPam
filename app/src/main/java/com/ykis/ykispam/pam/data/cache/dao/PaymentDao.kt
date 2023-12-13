package com.ykis.ykispam.pam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import androidx.room.Query

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(payments:List<PaymentEntity>)
    @Query("select * from payment where address_id = :addressId")
    fun getPaymentFromFlat(addressId:Int): List<PaymentEntity>
    @Query("delete from payment")
    fun deleteAllPayment()
    @Query("select year  from payment where address_id= :addressId group by year order by year DESC")
    fun getYearsByFlat(addressId: Int):List<Int>
    @Query("select * from payment where address_id = :addressId and year = :year ORDER BY data DESC")
    fun getPaymentsFromYearsFlat(addressId: Int , year: Int):List<PaymentEntity>
}