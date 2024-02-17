package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.appartment.ApartmentRemote
import com.ykis.ykispam.data.remote.core.BaseResponse
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(
    private val apartmentRemote: ApartmentRemote,
    private val userCache: UserCache
) : ApartmentRepository {
    override fun deleteFlatByUser(
        addressId: Int
    ): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap apartmentRemote.deleteFlatByUser(
                    addressId,
                    it.uid
                )
            }
    }
    override fun getFlatById(addressId: Int): Either<Failure, ApartmentEntity> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap apartmentRemote.getFlatById(addressId, it.uid) }
    }

    override suspend fun getApartmentList(uid: String): List<ApartmentEntity> {
        return apartmentRemote.getApartmentList(uid)
    }

    override suspend fun updateBti(params : ApartmentEntity): BaseResponse {
        return apartmentRemote.updateBti(params)
    }

    override suspend fun getApartment(addressId: Int, uid: String): ApartmentEntity {
        return apartmentRemote.getApartment(addressId , uid)
    }

    override suspend fun deleteApartment(addressId: Int, uid: String): BaseResponse {
        return apartmentRemote.deleteApartment(addressId , uid)
    }

    override suspend fun addApartmentUser(code: String ,uid :String): GetSimpleResponse {
        return apartmentRemote.addApartment(code , uid , email= "rshulik74@stud.op.edu.ua")
    }
}