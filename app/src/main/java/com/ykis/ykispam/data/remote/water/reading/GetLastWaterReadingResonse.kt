package com.ykis.ykispam.data.remote.water.reading

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity

class GetLastWaterReadingResponse(
    success: Int,
    message: String,
    @Json(name = "water_reading")
    val waterReading: WaterReadingEntity
) : BaseResponse(success, message)