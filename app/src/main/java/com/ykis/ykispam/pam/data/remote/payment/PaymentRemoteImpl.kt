package com.ykis.ykispam.pam.data.remote.payment

import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : PaymentRemote {

    override fun getFlatPayments(
        addressId: Int,
        uid: String
    ): Either<Failure, List<PaymentEntity>> {
        return request.make(
            apiService.getFlatPayment(
                createGetFlatPaymentMap(
                    addressId,
                    uid
                )
            )
        ) {
            it.payments
        }
    }

    private fun createGetFlatPaymentMap(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.ADDRESS_ID, addressId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }
}