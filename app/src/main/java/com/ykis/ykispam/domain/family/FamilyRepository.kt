package com.ykis.ykispam.domain.family

import com.ykis.ykispam.domain.family.request.FamilyParams

interface FamilyRepository {
    suspend fun getFamilyList(params:FamilyParams): List<FamilyEntity>
}