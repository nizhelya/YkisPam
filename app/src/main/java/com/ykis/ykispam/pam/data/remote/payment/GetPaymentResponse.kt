package com.ykis.ykispam.pam.data.remote.payment

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.payment.PaymentEntity

class GetPaymentResponse(
    success: Int,
    message: String,
    val payments: List<PaymentEntity>
) : BaseResponse(success, message)