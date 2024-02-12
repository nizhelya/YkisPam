package com.ykis.ykispam.domain.payment.request

import com.ykis.ykispam.data.PaymentRepositoryImpl
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.payment.PaymentEntity
import javax.inject.Inject

class GetFlatPayment @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl
) : UseCase<List<PaymentEntity>, BooleanInt>() {
    override suspend fun run(params: BooleanInt) = paymentRepository.getFlatPayment(params)
}