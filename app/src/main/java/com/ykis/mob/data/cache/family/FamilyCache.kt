package com.ykis.mob.data.cache.family

import com.ykis.mob.domain.family.FamilyEntity

interface FamilyCache {
    fun addFamilyByUser(family: List<FamilyEntity>)
    fun getFamilyByApartment(addressId: Int): List<FamilyEntity>
    fun deleteAllFamily()
    fun deleteFamilyByApartment(addressIds: List<Int>)
}