package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.service.ServiceCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.remote.service.ServiceRemote
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.service.ServiceRepository
import com.ykis.ykispam.pam.domain.service.request.ServiceParams
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.onNext
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceCache: ServiceCache,
    private val serviceRemote: ServiceRemote,

    private val userCache: UserCache
) : ServiceRepository {
    override fun getFlatService(params: ServiceParams): Either<Failure, List<ServiceEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    serviceRemote.getFlatServices(
                        params.addressId,
                        params.houseId,
                        params.year,
                        params.service,
                        params.total,
                        it.uid
                    )
                } else {
                    Either.Right(
                        serviceCache.getServiceFromFlat(
                            params.addressId,
                            when (params.service) {
                                1.toByte() -> "voda"
                                2.toByte() -> "teplo"
                                3.toByte() -> "tbo"
                                else -> "kv"
                            }
                        )
                    )
                }
            }
            .onNext { serviceEntities ->
                serviceEntities.map {
                    // TODO: replace with current year 
                    if (params.year == "2024") {
                        serviceCache.addService(listOf(it))
                    }
                }
            }

    }


    override fun getTotalFlatService(addressId: Int): Either<Failure, ServiceEntity?> {
        return Either.Right(serviceCache.getTotalDebt(addressId))
    }
}