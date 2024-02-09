package com.ykis.ykispam.pam.domain.family

import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface FamilyRepository {
    fun getFamilyFromFlat(params: BooleanInt): Either<Failure, List<FamilyEntity>>
}