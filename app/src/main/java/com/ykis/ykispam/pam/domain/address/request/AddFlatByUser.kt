package com.ykis.ykispam.pam.domain.address.request

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class AddFlatByUser @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<GetSimpleResponse, Int>() {
    override suspend fun run(params: Int): Either<Failure, GetSimpleResponse> =
        addressRepository.addFlatByUser(params)
}