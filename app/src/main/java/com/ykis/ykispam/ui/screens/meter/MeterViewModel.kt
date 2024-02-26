package com.ykis.ykispam.ui.screens.meter

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.water.meter.request.GetWaterMeterList
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.ui.screens.service.WaterMeterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MeterViewModel @Inject constructor(
    private val getWaterMeterListUseCase : GetWaterMeterList,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _waterMeterState = MutableStateFlow(WaterMeterState())
    val waterMeterState = _waterMeterState.asStateFlow()

    fun getWaterMeterList(uid:String, addressId:Int) {
        this.getWaterMeterListUseCase(addressId, uid).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(
                        waterMeterList = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(error = result.message ?: "Unexpected error!")
                }
                is Resource.Loading -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }
}