package com.ykis.ykispam.domain.address.request

import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.address.AddressRepository
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class GetStreetsFromBlock @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<List<AddressEntity>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<AddressEntity>> =
        addressRepository.getStreetsFromBlock(params)
}