package com.ykis.ykispam.pam.data.cache.appartment

import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity


interface AppartmentCache {
    fun addAppartmentByUser(appartment:List<AppartmentEntity>)
    fun getAppartmentsByUser():List<AppartmentEntity>

    fun deleteAllAppartments()
    fun deleteFlat(addressId: Int)
    fun getAppartmentById(addressId: Int): AppartmentEntity
}