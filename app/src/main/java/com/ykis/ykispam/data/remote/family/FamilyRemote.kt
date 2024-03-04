package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.request.FamilyParams

interface FamilyRemote {
    suspend fun getFamilyList(
        params:FamilyParams
    ):List<FamilyEntity>
}