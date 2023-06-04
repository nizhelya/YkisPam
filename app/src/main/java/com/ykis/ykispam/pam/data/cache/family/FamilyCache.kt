package com.ykis.ykispam.pam.data.cache.family

import com.ykis.ykispam.pam.domain.family.FamilyEntity

interface FamilyCache {
    fun addFamilyByUser(family: List<FamilyEntity>)
    fun getFamilyFromFlat(addressId: Int): List<FamilyEntity>
    fun deleteFamilyFromFlat(addressId: List<Int>)
}