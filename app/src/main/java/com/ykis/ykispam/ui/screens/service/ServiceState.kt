package com.ykis.ykispam.ui.screens.service

import com.ykis.ykispam.domain.service.ServiceEntity

data class ServiceState(
    val services: List<ServiceEntity> = emptyList(),
    val error : String = "",
    val isLoading:Boolean = true
)