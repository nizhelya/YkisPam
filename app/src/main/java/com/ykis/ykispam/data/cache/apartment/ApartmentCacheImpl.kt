package com.ykis.ykispam.data.cache.apartment

import com.ykis.ykispam.data.cache.dao.ApartmentDao
import com.ykis.ykispam.domain.apartment.ApartmentEntity
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