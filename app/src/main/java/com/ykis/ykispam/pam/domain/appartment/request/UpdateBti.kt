package com.ykis.ykispam.pam.domain.appartment.request

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.appartment.AppartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class UpdateBti @Inject constructor(
    private val appartmentRepository: AppartmentRepository
) : UseCase<GetSimpleResponse, AppartmentEntity>() {

    override suspend fun run(params: AppartmentEntity) =
        appartmentRepository.updateBti(
            params.addressId,
            params.phone,
            params.email
        )
}
