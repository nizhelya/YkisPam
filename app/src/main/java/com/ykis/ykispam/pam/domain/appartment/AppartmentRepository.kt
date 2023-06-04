package com.ykis.ykispam.pam.domain.appartment

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AppartmentRepository {
    suspend  fun getAppartmentsByUser(needFetch: Boolean): Either<Failure, List<AppartmentEntity>>

    fun deleteFlatByUser(addressId: Int): Either<Failure, GetSimpleResponse>
    fun updateBti(addressId: Int, phone: String, email: String): Either<Failure, GetSimpleResponse>
    fun getFlatById(addressId: Int): Either<Failure, AppartmentEntity>
}