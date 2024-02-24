package com.ykis.ykispam.ui.screens.service.list

import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.ui.navigation.ContentDetail

data class TotalDebtState(
    val showDetail : Boolean = false,
    val serviceDetail: ContentDetail = ContentDetail.OSBB,
    val totalDebt : ServiceEntity = ServiceEntity(),
    val isLoading: Boolean = true
)