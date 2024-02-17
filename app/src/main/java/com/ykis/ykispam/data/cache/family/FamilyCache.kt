package com.ykis.ykispam.data.cache.family

import com.ykis.ykispam.domain.family.FamilyEntity

interface FamilyCache {
    fun addFamilyByUser(family: List<FamilyEntity>)
    fun getFamilyByApartment(addressId: Int): List<FamilyEntity>
    fun deleteAllFamily()
    fun deleteFamilyByApartment(addressIds: List<Int>)
}