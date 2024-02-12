package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.family.FamilyEntity

class GetFamilyResponse(
    success: Int,
    message: String,
    val family: List<FamilyEntity>
) : BaseResponse(success, message)