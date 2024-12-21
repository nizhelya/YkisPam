package com.ykis.mob.data.remote.family

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.family.FamilyEntity

class GetFamilyResponse(
    success: Int,
    message: String,
    val family: List<FamilyEntity>
) : BaseResponse(success, message)