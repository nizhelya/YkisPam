package com.ykis.ykispam.domain.family

import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface FamilyRepository {
    fun getFamilyFromFlat(params: BooleanInt): Either<Failure, List<FamilyEntity>>
}