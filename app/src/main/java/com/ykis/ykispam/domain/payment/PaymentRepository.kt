package com.ykis.ykispam.domain.payment

import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface PaymentRepository {
    fun getFlatPayment(params: BooleanInt): Either<Failure, List<PaymentEntity>>
}