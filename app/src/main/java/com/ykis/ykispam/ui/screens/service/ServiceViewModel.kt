package com.ykis.ykispam.ui.screens.service

import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.service.request.GetFlatService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val getFlatServiceUseCase: GetFlatService,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _serviceDetail = MutableStateFlow<List<ServiceEntity>>(emptyList())
    val serviceDetail: StateFlow<List<ServiceEntity>> get() = _serviceDetail.asStateFlow()

    fun getDetailService(
        uid:String,
        addressId: Int,
        houseId: Int,
        service: Byte,
        total: Byte,
        year: String,
        needFetch: Boolean = false
    ) {
        getFlatServiceUseCase(
            ServiceParams(
                uid = uid,
                addressId = addressId,
                houseId = houseId,
                service = service,
                total = total,
                year = year,
                needFetch = needFetch
            )
        ) { it ->
            it.either(::handleFailure) {
                handleDetailService(
                    it, uid,addressId, houseId, service, total, year, !needFetch
                )
            }
        }
    }

    private fun handleDetailService(
        services: List<ServiceEntity>,
        uid: String,
        addressId: Int,
        houseId: Int,
        service: Byte,
        total: Byte,
        year: String,
        fromCache: Boolean,
    ) {
        _serviceDetail.value = services
         updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getDetailService(uid,addressId, houseId, service, total, year, true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getFlatServiceUseCase.unsubscribe()
    }
}