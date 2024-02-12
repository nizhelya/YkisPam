package com.ykis.ykispam.data.cache.family

import com.ykis.ykispam.domain.family.FamilyEntity

interface FamilyCache {
    fun addFamilyByUser(family: List<FamilyEntity>)
    fun getFamilyFromFlat(addressId: Int): List<FamilyEntity>
    fun deleteAllFamily()
}