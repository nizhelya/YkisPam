package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import javax.inject.Inject

class UpdateBti @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<GetSimpleResponse, ApartmentEntity>() {

    override suspend fun run(params: ApartmentEntity) =
        apartmentRepository.updateBti(
            params.addressId,
            params.phone,
            params.email
        )
}
