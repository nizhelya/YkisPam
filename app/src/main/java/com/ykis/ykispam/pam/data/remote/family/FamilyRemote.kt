package com.ykis.ykispam.pam.data.remote.family

import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface FamilyRemote {
    fun getFamilyFromFlat(
        addressId: Int,
        uid: String
    ): Either<Failure, List<FamilyEntity>>
}