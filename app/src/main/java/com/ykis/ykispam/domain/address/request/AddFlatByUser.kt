package com.ykis.ykispam.domain.address.request

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.address.AddressRepository
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class AddFlatByUser @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<GetSimpleResponse, String>() {
    override suspend fun run(params: String): Either<Failure, GetSimpleResponse> =
        addressRepository.addFlatByUser(params)
}