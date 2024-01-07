package com.ykis.ykispam.pam.screens.appartment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.service.request.ServiceParams
import com.ykis.ykispam.pam.domain.service.request.getFlatService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val getFlatServiceUseCase: getFlatService,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _servicesFlat = MutableLiveData<List<ServiceEntity>>()
    val servicesFlat: LiveData<List<ServiceEntity>> get() = _servicesFlat

    private val _serviceDetail = MutableLiveData<List<ServiceEntity>>()
    val serviceDetail: LiveData<List<ServiceEntity>> get() = _serviceDetail

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