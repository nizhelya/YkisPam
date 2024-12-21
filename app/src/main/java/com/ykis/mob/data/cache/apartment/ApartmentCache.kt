package com.ykis.mob.data.cache.apartment

import com.ykis.mob.domain.apartment.ApartmentEntity


interface ApartmentCache {
    fun insertApartmentList(apartment:List<ApartmentEntity>)
    fun getApartmentsByUser():List<ApartmentEntity>

    fun deleteAllApartments()
    fun deleteFlat(addressId: Int)
    fun getApartmentById(addressId: Int): ApartmentEntity?
}