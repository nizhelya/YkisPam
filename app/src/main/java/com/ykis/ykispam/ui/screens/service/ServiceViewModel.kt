package com.ykis.ykispam.ui.screens.service

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.GetFlatServices
import com.ykis.ykispam.domain.service.request.GetTotalDebtServices
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.ui.screens.service.list.TotalDebtState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val getFlatServiceUseCase: GetFlatServices,
    private val getTotalDebtServices: GetTotalDebtServices,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _detailState = MutableStateFlow<ServiceState>(ServiceState())
    val detailState: StateFlow<ServiceState> = _detailState.asStateFlow()

    private val _totalDebtState = MutableStateFlow<TotalDebtState>(TotalDebtState())
    val totalDebtState = _totalDebtState.asStateFlow()

    private val _totalDebt = MutableStateFlow<ServiceEntity?>(null)
    val totalDebt : StateFlow<ServiceEntity?> get() = _totalDebt.asStateFlow()

    private val _totalServiceDebt = MutableStateFlow<List<Float>>(emptyList())
    val totalServiceDebt: StateFlow<List<Float>> = _totalServiceDebt.asStateFlow()

    fun getTotalServiceDebt(params: ServiceParams){
        this.getTotalDebtServices(
            params = params
        ).onEach {
            result ->
            when(result){
                is Resource.Success -> {
                    this._totalDebtState.value = TotalDebtState(totalDebt = result.data!! , isLoading = false)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    this._totalDebtState.value = TotalDebtState(isLoading = true)
                }
            }
        }.launchIn(this.viewModelScope)
    }
     fun getDetailService(params: ServiceParams){
        this.getFlatServiceUseCase(
            params = params
        ).onEach {
            result->
            when(result){
                is Resource.Success -> {
                    this._detailState.value = ServiceState(services = result.data ?: emptyList() , isLoading = false)
                }
                is Resource.Error -> {
                    this._detailState.value = ServiceState(error = result.message ?: "Unexpected error!")
                }
                is Resource.Loading -> {
                    this._detailState.value = ServiceState(isLoading = true)
                }
            }
        }.launchIn(this.viewModelScope)
    }
}