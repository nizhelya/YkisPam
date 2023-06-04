package com.ykis.ykispam.pam.domain.appartment.request

import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.appartment.AppartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class GetAppartments @Inject constructor(
    private val appartmentRepository: AppartmentRepository
) : UseCase<List<AppartmentEntity>, Boolean>() {

    override suspend fun run(needFetch: Boolean) =
        appartmentRepository.getAppartmentsByUser(needFetch)
}