package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.payment.PaymentCache
import com.ykis.ykispam.data.remote.payment.PaymentRemote
import com.ykis.ykispam.domain.payment.PaymentRepository
import javax.inject.Inject


class PaymentRepositoryImpl @Inject constructor(
    private val paymentCache: PaymentCache,
    private val paymentRemote: PaymentRemote,
) : PaymentRepository {
//    override fun getFlatPayment(params: BooleanInt): Either<Failure, List<PaymentEntity>> {
//        return userCache.getCurrentUser()
//            .flatMap {
//                return@flatMap if (params.needFetch) {
//                    paymentRemote.getFlatPayments(
//                        params.int,
//                        it.uid
//                    )
//                } else {
//                    Either.Right(
//                        paymentCache.getPaymentsFromFlat(params.int)
//                    )
//                }
//            }
//            .onNext {
//                it.map {
//                    paymentCache.addPayments(listOf(it))
//                }
//            }
//    }
}