package com.ykis.ykispam.pam.data.cache.family

import com.ykis.ykispam.pam.data.cache.dao.FamilyDao
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyCacheImpl @Inject constructor(
    private val familyDao: FamilyDao
) : FamilyCache {
    override fun addFamilyByUser(family: List<FamilyEntity>) {
        familyDao.insertFamily(family)
    }

    override fun getFamilyFromFlat(addressId: Int): List<FamilyEntity> {
        return familyDao.getFamilyFromFlat(addressId)
    }

    override fun deleteFamilyFromFlat(addressId: List<Int>) {
        familyDao.deleteFamilyFromFlat(addressId)
    }


}