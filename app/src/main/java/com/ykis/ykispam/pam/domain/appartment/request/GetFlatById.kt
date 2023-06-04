package com.ykis.ykispam.pam.domain.appartment.request

import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.appartment.AppartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class GetFlatById @Inject constructor(
    private val appartmentRepository: AppartmentRepository
) : UseCase<AppartmentEntity, Int>() {

    override suspend fun run(params: Int) = appartmentRepository.getFlatById(params)
}