package com.ykis.ykispam.domain.payment

import com.ykis.ykispam.data.remote.payment.GetPaymentResponse

interface PaymentRepository {
   suspend fun getPaymentList(addressId:Int , year:String , uid:String):GetPaymentResponse
}