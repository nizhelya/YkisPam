package com.ykis.ykispam.domain.family

import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.family.request.FamilyParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface FamilyRepository {
    suspend fun getFamilyList(params:FamilyParams): List<FamilyEntity>
}