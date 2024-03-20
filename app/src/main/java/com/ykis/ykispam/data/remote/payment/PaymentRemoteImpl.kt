package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.payment.request.InsertPaymentParams
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

    override suspend fun insertPayment(params: InsertPaymentParams): InsertPaymentResponse {
        return apiService.insertPayment(
            createInsertPaymentMap(params)
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

    private fun createInsertPaymentMap(
        params: InsertPaymentParams
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.UID] = params.uid
        map[ApiService.ADDRESS_ID] = params.addressId.toString()
        map[ApiService.KVARTPLATA] = params.kvartplata.toString()
        map[ApiService.RFOND] = params.rfond.toString()
        map[ApiService.TEPLO] = params.teplo.toString()
        map[ApiService.VODA] = params.voda.toString()
        map[ApiService.TBO] = params.tbo.toString()

        return map
    }
}