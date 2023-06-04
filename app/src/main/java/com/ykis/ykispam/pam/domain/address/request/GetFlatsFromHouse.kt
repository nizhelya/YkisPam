package com.ykis.ykispam.pam.domain.address.request

import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class GetFlatsFromHouse @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<List<AddressEntity>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<AddressEntity>> =
        addressRepository.getFlatsFromHouse(params)
}