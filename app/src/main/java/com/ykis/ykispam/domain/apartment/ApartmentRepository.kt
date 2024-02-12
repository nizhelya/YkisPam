package com.ykis.ykispam.domain.apartment

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ApartmentRepository {
    suspend  fun getApartmentsByUser(needFetch: Boolean): Either<Failure, List<ApartmentEntity>>
    fun deleteFlatByUser(addressId: Int): Either<Failure, GetSimpleResponse>
    fun updateBti(addressId: Int, phone: String, email: String): Either<Failure, GetSimpleResponse>
    fun getFlatById(addressId: Int): Either<Failure, ApartmentEntity>
}