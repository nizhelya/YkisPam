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
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(
    private val apartmentRemote: ApartmentRemote,
    private val apartmentCache: ApartmentCache,
    private val familyCache: FamilyCache,
    private val serviceCache: ServiceCache,
    private val paymentCache: PaymentCache,
    private val waterMeterCache: WaterMeterCache,
    private val heatMeterCache: HeatMeterCache,
    private val waterReadingCache: WaterReadingCache,
    private val heatReadingCache: HeatReadingCache,
    private val userCache: UserCache
) : ApartmentRepository {
    private val addressIdList = mutableListOf<Int>()

//    override suspend fun getApartmentsByUser(needFetch: Boolean): Either<Failure, List<ApartmentEntity>> {
//        return userCache.getCurrentUser()
//            .flatMap { it ->
//                if (needFetch) {
//                    return@flatMap apartmentRemote.getApartmentsByUser(it.uid)
//
//                        .map { it.sortedBy { it.address } }
//                        .onNext {
//                            apartmentCache.deleteAllApartments()
//                        }
//                        .onNext {
//                            familyCache.deleteAllFamily()
//                            serviceCache.deleteAllService()
//                            paymentCache.deleteAllPayment()
//                            waterMeterCache.deleteAllWaterMeter()
//                            heatMeterCache.deleteAllHeatMeter()
//                            waterReadingCache.deleteAllWaterReading()
//                            heatReadingCache.deleteAllHeatReading()
////                            addressIdList.clear()
//                        }
//                        .onNext {
//                            it.map {
//                                apartmentCache.addApartmentByUser(listOf(it))
//                            }
//                        }
//                } else {
//                    return@flatMap Either.Right(apartmentCache.getApartmentsByUser())
//                        .map { it.sortedBy { it.address } }
//                }
//            }
//    }


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
}