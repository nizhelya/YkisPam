package com.ykis.ykispam.pam.data.cache.service

import com.ykis.ykispam.pam.domain.service.ServiceEntity

interface ServiceCache {
    fun addService(service: List<ServiceEntity>)
    fun getServiceFromFlat(addressId: Int, service: String): List<ServiceEntity>
    fun deleteAllService()
    fun getTotalDebt(addressId: Int): ServiceEntity?



}