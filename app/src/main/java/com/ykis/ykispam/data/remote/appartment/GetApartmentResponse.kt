package com.ykis.ykispam.data.remote.appartment

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity

class GetApartmentResponse(
    success: Int,
    message: String,
    val apartment: ApartmentEntity
) : BaseResponse(success, message)