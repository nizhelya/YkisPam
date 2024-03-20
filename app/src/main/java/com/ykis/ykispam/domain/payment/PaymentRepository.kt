package com.ykis.ykispam.domain.payment

import com.ykis.ykispam.data.remote.payment.GetPaymentResponse
import com.ykis.ykispam.data.remote.payment.InsertPaymentResponse
import com.ykis.ykispam.domain.payment.request.InsertPaymentParams

interface PaymentRepository {
   suspend fun getPaymentList(addressId:Int , year:String , uid:String):GetPaymentResponse
   suspend fun insertPayment(params:InsertPaymentParams) : InsertPaymentResponse
}