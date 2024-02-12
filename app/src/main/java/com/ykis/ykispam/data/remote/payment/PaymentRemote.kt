package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface PaymentRemote {
    fun getFlatPayments(
        addressId: Int,
        uid: String
    ): Either<Failure, List<PaymentEntity>>
}