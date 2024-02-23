package com.ykis.ykispam.ui.screens.service.list

import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.ui.navigation.ContentDetail

data class TotalDebtState(
    val serviceDetail: ContentDetail = ContentDetail.EMPTY,
    val totalDebt : ServiceEntity = ServiceEntity(),
    val isLoading: Boolean = true
)