package com.ykis.mob.data.remote.appartment

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.apartment.ApartmentEntity

class GetApartmentResponse(
    success: Int,
    message: String,
    val apartment: ApartmentEntity
) : BaseResponse(success, message)