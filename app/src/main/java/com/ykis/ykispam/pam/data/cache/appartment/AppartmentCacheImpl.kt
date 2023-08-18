package com.ykis.ykispam.pam.data.cache.appartment

import com.ykis.ykispam.pam.data.cache.dao.AppartmentDao
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppartmentCacheImpl @Inject constructor(
    private val appartmentDao: AppartmentDao
): AppartmentCache {


    override fun addAppartmentByUser(appartment: List<AppartmentEntity>) {
        appartmentDao.addAppartmentByUser(appartment)
    }

    override fun getAppartmentsByUser(): List<AppartmentEntity> {
        return appartmentDao.getAppartmentsByUser()
    }


    override fun deleteAllAppartments() {
        appartmentDao.deleteAllAppartments()
    }

    override fun deleteFlat(addressId: Int) {
        appartmentDao.deleteFlat(addressId)
    }

    override fun getAppartmentById(addressId: Int): AppartmentEntity {
        return appartmentDao.getFlatById(addressId)
    }


}