package com.ykis.mob.data.cache.payment

import com.ykis.mob.data.cache.dao.PaymentDao
import com.ykis.mob.domain.payment.PaymentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentCacheImpl @Inject constructor(
    private val paymentDao: PaymentDao
) : PaymentCache {
    override fun addPayments(payments: List<PaymentEntity>) {
        paymentDao.insertPayment(payments)
    }

    override fun getPaymentsFromFlat(addressId: Int): List<PaymentEntity> {
        return paymentDao.getPaymentFromFlat(addressId)
    }

    override fun deleteAllPayment() {
        paymentDao.deleteAllPayment()
    }

    override suspend fun deletePaymentByApartment(addressIds: List<Int>) {
        paymentDao.deletePaymentByApartment(addressIds)
    }
}