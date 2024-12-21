package com.ykis.mob.domain.family

import com.ykis.mob.domain.family.request.FamilyParams

interface FamilyRepository {
    suspend fun getFamilyList(params:FamilyParams): List<FamilyEntity>
}