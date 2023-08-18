package com.ykis.ykispam.pam.data.remote.appartment

import com.ykis.ykispam.core.Response
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface AppartmentRemote {
    fun getAppartmentsByUser(uid: String): Either<Failure, List<AppartmentEntity>>

    fun deleteFlatByUser(
        addressId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun updateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun getFlatById(addressId: Int,uid: String): Either<Failure, AppartmentEntity>
}