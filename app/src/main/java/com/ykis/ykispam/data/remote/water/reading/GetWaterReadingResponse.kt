package com.ykis.ykispam.data.remote.water.reading

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity

class GetWaterReadingResponse(
    success: Int,
    message: String,
    @Json(name = "water_readings")
    val waterReadings: List<WaterReadingEntity>
) : BaseResponse(success, message)