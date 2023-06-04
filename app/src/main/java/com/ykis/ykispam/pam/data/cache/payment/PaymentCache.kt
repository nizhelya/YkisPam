package com.ykis.ykispam.pam.data.cache.payment

import com.ykis.ykispam.pam.domain.payment.PaymentEntity

interface PaymentCache {
    fun addPayments(payments: List<PaymentEntity>)
    fun getPaymentsFromFlat(addressId: Int): List<PaymentEntity>
    fun deletePaymentFromFlat(addressId: List<Int>)
    fun getYearsFromFlat(addressId: Int): List<Int>
    suspend fun getPaymentFromYearFlat(addressId: Int, year: Int): List<PaymentEntity>
}