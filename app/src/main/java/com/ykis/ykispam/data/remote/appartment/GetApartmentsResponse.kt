package com.ykis.ykispam.data.remote.appartment

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity

class GetApartmentsResponse(
    success: Int,
    message: String,
    val apartments: List<ApartmentEntity>
) : BaseResponse(success, message)