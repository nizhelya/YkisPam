package com.ykis.ykispam.pam.data.remote.address

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface AddressRemote {
    fun getBlocks(uid: String): Either<Failure, List<AddressEntity>>
    fun getStreetsFromBlock(
        blockId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>>

    fun getHousesFromStreet(
        streetId: Int,
        blockId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>>

    fun getFlatsFromHouse(
        houseId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>>

    fun addFlatsByUser(
        kod: String,
        uid: String,
        email:String
    ): Either<Failure, GetSimpleResponse>

}