package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.data.remote.api.ApiService
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : PaymentRemote {

    override suspend fun getPaymentList(addressId: Int, year: String, uid: String): GetPaymentResponse {
        return apiService.getFlatPayment(
            createGetPaymentListMap(
                addressId,year,uid
            )
        ).await()
    }

    private fun createGetPaymentListMap(
        addressId: Int,
        year: String,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.YEAR] = year
        map[ApiService.UID] = uid
        return map
    }

}