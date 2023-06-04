package com.ykis.ykispam.pam.data

import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.pam.data.cache.appartment.AppartmentCache
import com.ykis.ykispam.pam.data.cache.family.FamilyCache
import com.ykis.ykispam.pam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.pam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.pam.data.cache.payment.PaymentCache
import com.ykis.ykispam.pam.data.cache.service.ServiceCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.pam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.appartment.AppartmentRepository
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.map
import com.ykis.ykispam.pam.domain.type.onNext
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.appartment.AppartmentRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AppartmentRepositoryImpl @Inject constructor(
    private val appartmentRemote: AppartmentRemote,
    private val appartmentCache: AppartmentCache,
    private val firebaseService: FirebaseService,
    private val familyCache: FamilyCache,
    private val serviceCache: ServiceCache,
    private val paymentCache: PaymentCache,
    private val waterMeterCache: WaterMeterCache,
    private val heatMeterCache: HeatMeterCache,
    private val waterReadingCache: WaterReadingCache,
    private val heatReadingCache: HeatReadingCache,
    private val userCache: UserCache
) : AppartmentRepository {
    private val addressIdList = mutableListOf<Int>()

    override suspend fun getAppartmentsByUser(needFetch: Boolean): Either<Failure, List<AppartmentEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (needFetch) {
                    appartmentRemote.getAppartmentsByUser(it.userId, it.token)

                } else {
                    Either.Right(
                        appartmentCache.getAppartmentsByUser()
                    )
                }
            }
            .map { it.sortedBy { it.address } }
            .onNext {
                appartmentCache.deleteAllAppartments()
            }
            .onNext {
                for (i in it) {
                    addressIdList.add(i.addressId)
                }
                familyCache.deleteFamilyFromFlat(addressIdList)
                serviceCache.deleteServiceFromFlat(addressIdList)
                paymentCache.deletePaymentFromFlat(addressIdList)
                waterMeterCache.deleteWaterMeter(addressIdList)
                heatMeterCache.deleteHeatMeter(addressIdList)
                waterReadingCache.deleteReadingFromFlat(addressIdList)
                heatReadingCache.deleteReadingFromFlat(addressIdList)
                addressIdList.clear()
            }
            .onNext {
                it.map {
                    appartmentCache.addAppartmentByUser(listOf(it))
                }
            }
    }



    override fun deleteFlatByUser(
        addressId: Int
    ): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap appartmentRemote.deleteFlatByUser(
                    addressId,
                    it.userId,
                    it.token
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
                return@flatMap appartmentRemote.updateBti(
                    addressId,
                    phone,
                    email,
                    it.userId,
                    it.token
                )
            }
    }

    override fun getFlatById(addressId: Int): Either<Failure, AppartmentEntity> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap appartmentRemote.getFlatById(addressId, it.userId, it.token) }
    }
}