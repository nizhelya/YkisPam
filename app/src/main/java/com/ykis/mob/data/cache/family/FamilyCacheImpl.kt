package com.ykis.mob.data.cache.family

import com.ykis.mob.data.cache.dao.FamilyDao
import com.ykis.mob.domain.family.FamilyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyCacheImpl @Inject constructor(
    private val familyDao: FamilyDao
) : FamilyCache {
    override fun addFamilyByUser(family: List<FamilyEntity>) {
        familyDao.insertFamily(family)
    }

    override fun getFamilyByApartment(addressId: Int): List<FamilyEntity> {
        return familyDao.getFamilyByApartment(addressId)
    }

    override fun deleteAllFamily() {
        familyDao.deleteAllFamily()
    }

    override fun deleteFamilyByApartment(addressIds: List<Int>) {
        familyDao.deleteFamilyByApartment(addressIds)
    }


}