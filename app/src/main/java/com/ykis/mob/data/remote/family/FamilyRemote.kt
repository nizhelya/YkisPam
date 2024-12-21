package com.ykis.mob.data.remote.family

import com.ykis.mob.domain.family.FamilyEntity
import com.ykis.mob.domain.family.request.FamilyParams

interface FamilyRemote {
    suspend fun getFamilyList(
        params:FamilyParams
    ):List<FamilyEntity>
}