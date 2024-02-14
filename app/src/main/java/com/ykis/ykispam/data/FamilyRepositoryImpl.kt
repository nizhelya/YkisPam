package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.family.FamilyCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.FamilyRepository
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.map
import com.ykis.ykispam.domain.type.onNext
import com.ykis.ykispam.data.remote.family.FamilyRemote
import com.ykis.ykispam.domain.family.request.FamilyParams
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyRemote: FamilyRemote,
) : FamilyRepository {
    override suspend fun getFamilyList(params: FamilyParams): List<FamilyEntity> {
        return familyRemote.getFamilyList(params)
    }

}