package com.ykis.mob.data.remote.payment

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.payment.PaymentEntity

class GetPaymentResponse(
    success: Int,
    message: String,
    val payments: List<PaymentEntity>
) : BaseResponse(success, message)