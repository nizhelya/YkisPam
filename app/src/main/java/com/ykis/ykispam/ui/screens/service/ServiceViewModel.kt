package com.ykis.ykispam.ui.screens.service

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.service.request.ImproveGetFlatServices
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
//    private val getFlatServiceUseCase: GetFlatService,
    private val newGetFlatServiceUseCase: ImproveGetFlatServices,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _serviceDetail = MutableStateFlow<List<ServiceEntity>>(emptyList())
    val serviceDetail: StateFlow<List<ServiceEntity>> get() = _serviceDetail.asStateFlow()

    private val _state = MutableStateFlow<ServiceState>(ServiceState())
    val state: StateFlow<ServiceState> = _state.asStateFlow()

    init {
        refresh()
    }
    fun refresh(){
        newGetDetailService()
    }
    private fun newGetDetailService(){
        this.newGetFlatServiceUseCase().onEach {
            result->
            when(result){
                is Resource.Success -> {
                    this._state.value = ServiceState(services = result.data ?: emptyList())
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

   /* fun getDetailService(
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
*/
    override fun onCleared() {
        super.onCleared()
//        getFlatServiceUseCase.unsubscribe()
    }
}