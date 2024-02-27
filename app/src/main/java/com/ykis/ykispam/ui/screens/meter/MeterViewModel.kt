package com.ykis.ykispam.ui.screens.meter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.heat.meter.request.GetHeatMeterList
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.water.meter.request.GetWaterMeterList
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MeterViewModel @Inject constructor(
    private val getWaterMeterListUseCase : GetWaterMeterList,
    private val getHeatMeterListUseCase : GetHeatMeterList,
    private val logService: LogService
) : BaseViewModel(logService) {

    private val _waterMeterState = MutableStateFlow(WaterMeterState())
    val waterMeterState = _waterMeterState.asStateFlow()

    private val _heatMeterState = MutableStateFlow(HeatMeterState())
    val heatMeterState = _heatMeterState.asStateFlow()

    private val _showDetail = MutableStateFlow(false)
    val showDetail = _showDetail.asStateFlow()

    private val _contentDetail = MutableStateFlow(ContentDetail.WATER_METER)
    val contentDetail = _contentDetail.asStateFlow()

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

    fun getHeatMeterList(uid:String, addressId:Int) {
        this.getHeatMeterListUseCase(addressId, uid).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(
                        heatMeterList = result.data ?: emptyList(),
                        isLoading = false
                    )
                    Log.d("heat_test", result.data.toString())
                }
                is Resource.Error -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(error = result.message ?: "Unexpected error!")
                }
                is Resource.Loading -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }


    fun setWaterMeterDetail(waterMeterEntity: WaterMeterEntity){
        _waterMeterState.value = _waterMeterState.value.copy(
            selectedWaterMeter = waterMeterEntity
        )
        _contentDetail.value = ContentDetail.WATER_METER
        _showDetail.value = true
    }

    fun setHeatMeterDetail(heatMeterEntity: HeatMeterEntity){
        _heatMeterState.value = _heatMeterState.value.copy(
            selectedHeatMeter = heatMeterEntity
        )
        _contentDetail.value = ContentDetail.HEAT_METER
        _showDetail.value = true
    }

    fun closeContentDetail(){
        _showDetail.value = false
    }
}