package com.ykis.mob.data.remote.payment

import com.ykis.mob.domain.payment.request.InsertPaymentParams


interface PaymentRemote {
   suspend fun getPaymentList(addressId:Int , year:String ,uid:String) : GetPaymentResponse
   suspend fun insertPayment(params:InsertPaymentParams):InsertPaymentResponse
}