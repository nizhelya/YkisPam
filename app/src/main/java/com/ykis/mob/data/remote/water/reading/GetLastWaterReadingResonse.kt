package com.ykis.mob.data.remote.water.reading

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity

class GetLastWaterReadingResponse(
    success: Int,
    message: String,
    @Json(name = "water_reading")
    val waterReading: WaterReadingEntity
) : BaseResponse(success, message)