package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.payment.PaymentEntity

class GetPaymentResponse(
    success: Int,
    message: String,
    val payments: List<PaymentEntity>
) : BaseResponse(success, message)