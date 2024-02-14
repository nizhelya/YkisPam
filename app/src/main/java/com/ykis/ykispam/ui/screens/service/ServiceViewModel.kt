package com.ykis.ykispam.ui.screens.service

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.GetFlatServices
import com.ykis.ykispam.domain.service.request.ServiceParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class ServiceState(
    val services: List<ServiceEntity> = emptyList(),
    val error : String = "",
    val isLoading:Boolean = true
)
@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val getFlatServiceUseCase: GetFlatServices,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _state = MutableStateFlow<ServiceState>(ServiceState())
    val state: StateFlow<ServiceState> = _state.asStateFlow()


     fun newGetDetailService(params: ServiceParams){
        this.getFlatServiceUseCase(
            params = params
        ).onEach {
            result->
            when(result){
                is Resource.Success -> {
                    this._state.value = ServiceState(services = result.data ?: emptyList() , isLoading = false)
                }
                is Resource.Error -> {
                    this._state.value = ServiceState(error = result.message ?: "Unexpected error!")
                }
                is Resource.Loading -> {
                    this._state.value = ServiceState(isLoading = true)
                }
            }
        }.launchIn(this.viewModelScope)
    }
}