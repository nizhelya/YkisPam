package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.domain.payment.request.InsertPaymentParams


interface PaymentRemote {
   suspend fun getPaymentList(addressId:Int , year:String ,uid:String) : GetPaymentResponse
   suspend fun insertPayment(params:InsertPaymentParams):InsertPaymentResponse
}