package com.ykis.mob.data.cache.service

import com.ykis.mob.data.cache.dao.ServiceDao
import com.ykis.mob.domain.service.ServiceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceCacheImpl @Inject constructor(
    private val serviceDao: ServiceDao
) : ServiceCache {
    override fun addService(service: List<ServiceEntity>) {
        serviceDao.insertService(service)
    }

    override fun getServiceDetail(addressId: Int, service: String , year:String): List<ServiceEntity> {
        return serviceDao.getServiceDetail(addressId, service , year)
    }

    override fun deleteAllService() {
        serviceDao.deleteAllService()
    }

    override fun getTotalDebt(addressId: Int): ServiceEntity? {
        return serviceDao.getTotalDebt(addressId)
    }

    override fun deleteServiceByApartment(addressIds: List<Int>) {
        serviceDao.deleteServiceByApartment(addressIds)
    }
}