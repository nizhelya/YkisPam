package com.ykis.mob.data

import com.ykis.mob.data.remote.family.FamilyRemote
import com.ykis.mob.domain.family.FamilyEntity
import com.ykis.mob.domain.family.FamilyRepository
import com.ykis.mob.domain.family.request.FamilyParams
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyRemote: FamilyRemote,
) : FamilyRepository {
    override suspend fun getFamilyList(params: FamilyParams): List<FamilyEntity> {
        return familyRemote.getFamilyList(params)
    }
}