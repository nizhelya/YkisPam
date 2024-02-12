package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.payment.PaymentCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.payment.PaymentRemote
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.domain.payment.PaymentRepository
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.onNext
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentCache: PaymentCache,
    private val paymentRemote: PaymentRemote,
    private val userCache: UserCache
) : PaymentRepository {
    override fun getFlatPayment(params: BooleanInt): Either<Failure, List<PaymentEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    paymentRemote.getFlatPayments(
                        params.int,
                        it.uid
                    )
                } else {
                    Either.Right(
                        paymentCache.getPaymentsFromFlat(params.int)
                    )
                }
            }
            .onNext {
                it.map {
                    paymentCache.addPayments(listOf(it))
                }
            }
    }
}