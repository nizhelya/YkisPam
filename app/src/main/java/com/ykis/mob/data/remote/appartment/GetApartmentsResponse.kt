package com.ykis.mob.data.remote.appartment

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.apartment.ApartmentEntity

class GetApartmentsResponse(
    success: Int,
    message: String,
    val apartments: List<ApartmentEntity>
) : BaseResponse(success, message)