package com.ykis.ykispam.pam.data.cache.payment

import com.ykis.ykispam.pam.data.cache.dao.PaymentDao
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
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

    override fun deletePaymentFromFlat(addressId: List<Int>) {
        paymentDao.deletePaymentFromFlat(addressId)
    }

    override fun getYearsFromFlat(addressId: Int): List<Int> {
        return paymentDao.getYearsByFlat(addressId)
    }

    override suspend fun getPaymentFromYearFlat(addressId: Int, year: Int): List<PaymentEntity> {
        return paymentDao.getPaymentsFromYearsFlat(addressId, year)
    }
}