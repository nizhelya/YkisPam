package com.ykis.ykispam.data.remote.address

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

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