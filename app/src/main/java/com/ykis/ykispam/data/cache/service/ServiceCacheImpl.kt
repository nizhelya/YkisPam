package com.ykis.ykispam.data.cache.service

import com.ykis.ykispam.data.cache.dao.ServiceDao
import com.ykis.ykispam.domain.service.ServiceEntity
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

    override fun deleteAllService() {
        serviceDao.deleteAllService()
    }

    override fun getTotalDebt(addressId: Int): ServiceEntity? {
        return serviceDao.getTotalDebt(addressId)
    }


}