package com.ykis.mob.data.cache.payment

import com.ykis.mob.domain.payment.PaymentEntity

interface PaymentCache {
    fun addPayments(payments: List<PaymentEntity>)
    fun getPaymentsFromFlat(addressId: Int): List<PaymentEntity>
    fun deleteAllPayment()
    suspend fun deletePaymentByApartment(addressIds:List<Int>)
}