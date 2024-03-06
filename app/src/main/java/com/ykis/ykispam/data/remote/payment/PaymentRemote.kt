package com.ykis.ykispam.data.remote.payment


interface PaymentRemote {
   suspend fun getPaymentList(addressId:Int , year:String ,uid:String) : GetPaymentResponse
}