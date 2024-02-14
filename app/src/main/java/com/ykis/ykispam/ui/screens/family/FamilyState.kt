package com.ykis.ykispam.ui.screens.family

import com.ykis.ykispam.domain.family.FamilyEntity

data class FamilyState(
    val familyList: List<FamilyEntity> = emptyList(),
    val isLoading:Boolean = true,
    val error : String = ""
)