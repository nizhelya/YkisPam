package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.apartment.ApartmentCache
import com.ykis.ykispam.data.cache.family.FamilyCache
import com.ykis.ykispam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.data.cache.payment.PaymentCache
import com.ykis.ykispam.data.cache.service.ServiceCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.map
import com.ykis.ykispam.domain.type.onNext
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

    override fun updateBti(
        addressId: Int,
        phone: String,
        email: String
    ): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap apartmentRemote.updateBti(
                    addressId,
                    phone,
                    email,
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

    override suspend fun newUpdateBti(params : ApartmentEntity): BaseResponse {
        return apartmentRemote.newUpdateBti(params)
    }
}