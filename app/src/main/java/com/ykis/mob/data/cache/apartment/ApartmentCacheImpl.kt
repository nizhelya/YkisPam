package com.ykis.mob.data.cache.apartment

import com.ykis.mob.data.cache.dao.ApartmentDao
import com.ykis.mob.domain.apartment.ApartmentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApartmentCacheImpl @Inject constructor(
    private val apartmentDao: ApartmentDao
): ApartmentCache {


    override fun insertApartmentList(apartment: List<ApartmentEntity>) {
        apartmentDao.insertApartmentList(apartment)
    }

    override fun getApartmentsByUser(): List<ApartmentEntity> {
        return apartmentDao.getApartmentList()
    }


    override fun deleteAllApartments() {
        apartmentDao.deleteAllApartments()
    }

    override fun deleteFlat(addressId: Int) {
        apartmentDao.deleteFlat(addressId)
    }

    override fun getApartmentById(addressId: Int): ApartmentEntity? {
        return apartmentDao.getFlatById(addressId)
    }


}