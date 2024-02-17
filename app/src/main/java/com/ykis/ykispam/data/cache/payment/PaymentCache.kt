package com.ykis.ykispam.data.cache.payment

import com.ykis.ykispam.domain.payment.PaymentEntity

interface PaymentCache {
    fun addPayments(payments: List<PaymentEntity>)
    fun getPaymentsFromFlat(addressId: Int): List<PaymentEntity>
    fun deleteAllPayment()
    fun getYearsFromFlat(addressId: Int): List<Int>
    suspend fun getPaymentFromYearFlat(addressId: Int, year: Int): List<PaymentEntity>

    fun deletePaymentByApartment(addressIds:List<Int>)
}