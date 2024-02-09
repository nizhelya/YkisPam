package com.ykis.ykispam.pam.data.remote.family

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.family.FamilyEntity

class GetFamilyResponse(
    success: Int,
    message: String,
    val family: List<FamilyEntity>
) : BaseResponse(success, message)