package com.ykis.ykispam.pam.data.cache.service

import com.ykis.ykispam.pam.data.cache.dao.ServiceDao
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceCacheImpl @Inject constructor(
    private val serviceDao: ServiceDao
) : ServiceCache {
    override fun addService(service: List<ServiceEntity>) {
        serviceDao.insertService(service)
    }

    override fun getServiceFromFlat(addressId: Int, service: String): List<ServiceEntity> {
        return serviceDao.getServiceFromFlat(addressId, service)
    }

    override fun deleteServiceFromFlat(addressId: List<Int>) {
        serviceDao.deleteServiceFromFlat(addressId)
    }

    override fun getTotalDebt(addressId: Int): ServiceEntity? {
        return serviceDao.getTotalDebt(addressId)
    }

}