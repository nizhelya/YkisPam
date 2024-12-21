package com.ykis.mob.data.remote.payment

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse

class InsertPaymentResponse(
    success: Int,
    message: String,
    @Json(name = "payment_id")
    val paymentId: Int,
    val uri : String
) : BaseResponse(success, message)