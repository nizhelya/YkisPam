package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.payment.GetPaymentResponse
import com.ykis.ykispam.data.remote.payment.InsertPaymentResponse
import com.ykis.ykispam.data.remote.payment.PaymentRemote
import com.ykis.ykispam.domain.payment.PaymentRepository
import com.ykis.ykispam.domain.payment.request.InsertPaymentParams
import javax.inject.Inject


class PaymentRepositoryImpl @Inject constructor(
    private val paymentRemote: PaymentRemote,
) : PaymentRepository {
    override suspend fun getPaymentList(addressId: Int, year: String, uid: String): GetPaymentResponse {
        return paymentRemote.getPaymentList(addressId , year, uid)
    }

    override suspend fun insertPayment(params: InsertPaymentParams): InsertPaymentResponse {
        return paymentRemote.insertPayment(params)
    }

}