package com.ykis.ykispam.pam.domain.payment

import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface PaymentRepository {
    fun getFlatPayment(params: BooleanInt): Either<Failure, List<PaymentEntity>>
}