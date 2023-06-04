package com.ykis.ykispam.pam.data.remote.appartment

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity

class GetAppartmentsResponse(
    success: Int,
    message: String,
    val appartments: List<AppartmentEntity>
) : BaseResponse(success, message)