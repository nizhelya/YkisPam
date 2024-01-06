package com.ykis.ykispam.pam.data.remote.service

import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : ServiceRemote {
    override fun getFlatServices(
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
        uid: String
    ): Either<Failure, List<ServiceEntity>> {
        return request.make(
            apiService.getFlatService(
                createGetFlatServiceMap(
                    addressId,
                    houseId,
                    year,
                    service,
                    total,
                    uid
                )
            )
        )
        {
            it.services
        }
    }


    private fun createGetFlatServiceMap(
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.ADDRESS_ID, addressId.toString())
        map.put(ApiService.HOUSE_ID, houseId.toString())
        map.put(ApiService.YEAR, year)
        map.put(ApiService.SERVICE, service.toString())
        map.put(ApiService.TOTAL, total.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

}