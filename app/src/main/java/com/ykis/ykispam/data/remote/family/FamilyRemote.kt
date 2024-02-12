package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface FamilyRemote {
    fun getFamilyFromFlat(
        addressId: Int,
        uid: String
    ): Either<Failure, List<FamilyEntity>>
}