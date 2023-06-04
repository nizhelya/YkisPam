package com.ykis.ykispam.pam.data.remote.payment

import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface PaymentRemote {
    fun getFlatPayments(
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<PaymentEntity>>
}