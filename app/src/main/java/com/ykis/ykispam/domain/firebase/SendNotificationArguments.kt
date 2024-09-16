package com.ykis.ykispam.domain.firebase

import com.squareup.moshi.Json

data class SendNotificationArguments(
    @Json(name = "recipient_token")
    val recipientToken : String,
    val title : String,
    val body : String
)