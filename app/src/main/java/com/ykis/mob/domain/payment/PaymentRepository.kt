package com.ykis.mob.domain.payment

import com.ykis.mob.data.remote.payment.GetPaymentResponse
import com.ykis.mob.data.remote.payment.InsertPaymentResponse
import com.ykis.mob.domain.payment.request.InsertPaymentParams

interface PaymentRepository {
   suspend fun getPaymentList(addressId:Int , year:String , uid:String):GetPaymentResponse
   suspend fun insertPayment(params:InsertPaymentParams) : InsertPaymentResponse
}