package com.ykis.ykispam.pam.data.remote.address

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface AddressRemote {
    fun getBlocks(userId: Int, token: String): Either<Failure, List<AddressEntity>>
    fun getStreetsFromBlock(
        blockId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<AddressEntity>>

    fun getHousesFromStreet(
        streetId: Int,
        blockId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<AddressEntity>>

    fun getFlatsFromHouse(
        houseId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<AddressEntity>>

    fun addFlatsByUser(
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>

    fun checkCode(
        kod: String,
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>
}