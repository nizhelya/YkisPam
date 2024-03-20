package com.ykis.ykispam.data.remote.payment

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse

class InsertPaymentResponse(
    success: Int,
    message: String,
    @Json(name = "payment_id")
    val paymentId: Int
) : BaseResponse(success, message)