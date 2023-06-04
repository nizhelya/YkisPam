package com.ykis.ykispam.pam.domain.family.request

import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.family.FamilyRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class GetFamilyFromFlat @Inject constructor(
    private val familyRepository: FamilyRepository
) : UseCase<List<FamilyEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt) = familyRepository.getFamilyFromFlat(params)
}

data class BooleanInt(
    val int: Int,
    val needFetch: Boolean
)