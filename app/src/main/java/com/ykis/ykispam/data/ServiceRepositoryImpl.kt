package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.service.ServiceCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.onNext
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
                        it.uid,
                        params.addressId,
                        params.houseId,
                        params.year,
                        params.service,
                        params.total,
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
                    if (params.total== 1.toByte()) {
                        serviceCache.addService(listOf(it))
                    }
                }
            }

    }


    override fun getTotalFlatService(addressId: Int): Either<Failure, ServiceEntity?> {
        return Either.Right(serviceCache.getTotalDebt(addressId))
    }
}