package com.ykis.ykispam.pam.domain.payment.request

import com.ykis.ykispam.pam.data.PaymentRepositoryImpl
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import javax.inject.Inject

class GetFlatPayment @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl
) : UseCase<List<PaymentEntity>, BooleanInt>() {
    override suspend fun run(params: BooleanInt) = paymentRepository.getFlatPayment(params)
}