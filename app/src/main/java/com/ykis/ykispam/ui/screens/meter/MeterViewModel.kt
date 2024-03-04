package com.ykis.ykispam.ui.screens.meter

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.meter.heat.meter.request.GetHeatMeterList
import com.ykis.ykispam.domain.meter.heat.reading.AddHeatReadingParams
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.meter.heat.reading.request.AddHeatReading
import com.ykis.ykispam.domain.meter.heat.reading.request.DeleteLastHeatReading
import com.ykis.ykispam.domain.meter.heat.reading.request.GetHeatReadings
import com.ykis.ykispam.domain.meter.heat.reading.request.GetLastHeatReading
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.meter.water.meter.request.GetWaterMeterList
import com.ykis.ykispam.domain.meter.water.reading.AddWaterReadingParams
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.domain.meter.water.reading.request.AddWaterReading
import com.ykis.ykispam.domain.meter.water.reading.request.DeleteLastWaterReading
import com.ykis.ykispam.domain.meter.water.reading.request.GetLastWaterReading
import com.ykis.ykispam.domain.meter.water.reading.request.GetWaterReadings
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
    private val getWaterMeterListUseCase: GetWaterMeterList,
    private val getLastWaterReadingUseCase: GetLastWaterReading,
    private val getWaterReadingsUseCase: GetWaterReadings,
    private val addWaterReadingUseCase: AddWaterReading,
    private val deleteLastWaterReadingUseCse: DeleteLastWaterReading,
    private val getHeatMeterListUseCase: GetHeatMeterList,
    private val getHeatReadingsUseCase: GetHeatReadings,
    private val getLastHeatReadingUseCase: GetLastHeatReading,
    private val addHeatReadingUseCase : AddHeatReading,
    private val deleteLastHeatReadingUseCase: DeleteLastHeatReading,
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

    fun getWaterMeterList(uid: String, addressId: Int) {
        this.getWaterMeterListUseCase(addressId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(
                        waterMeterList = result.data ?: emptyList(),
                        // TODO: make loading
                        isMetersLoading = false
                    )
                }

                is Resource.Error -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(
                        error = result.message ?: "Unexpected error!",
                        isMetersLoading = true
                    )
                }

                is Resource.Loading -> {
                    this._waterMeterState.value = _waterMeterState.value.copy(
                        isMetersLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun getHeatMeterList(uid: String, addressId: Int) {
        this.getHeatMeterListUseCase(addressId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(
                        heatMeterList = result.data ?: emptyList(),
                        isMetersLoading = false
                    )
                }

                is Resource.Error -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(
                        error = result.message ?: "Unexpected error!",
                        isMetersLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._heatMeterState.value = _heatMeterState.value.copy(
                        isMetersLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }


    fun setWaterMeterDetail(waterMeterEntity: WaterMeterEntity) {
        _waterMeterState.value = _waterMeterState.value.copy(
            selectedWaterMeter = waterMeterEntity
        )
        _contentDetail.value = ContentDetail.WATER_METER
        _showDetail.value = true
    }

    fun setHeatMeterDetail(heatMeterEntity: HeatMeterEntity) {
        _heatMeterState.value = _heatMeterState.value.copy(
            selectedHeatMeter = heatMeterEntity
        )
        _contentDetail.value = ContentDetail.HEAT_METER
        _showDetail.value = true
    }

    fun closeContentDetail() {
        _showDetail.value = false
    }

    fun getWaterReadings(uid: String, vodomerId: Int) {
        this.getWaterReadingsUseCase(vodomerId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        waterReadings = result.data ?: emptyList(),
                        isReadingsLoading = false,
                    )
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isReadingsLoading = true
                    )
                }

                is Resource.Loading -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isReadingsLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun getLastWaterReading(uid: String, vodomerId: Int) {
        this.getLastWaterReadingUseCase(vodomerId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        lastWaterReading = result.data ?: WaterReadingEntity(),
                        isLastReadingLoading = false
                    )
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun getHeatReadings(uid: String, teplomerId: Int) {
        this.getHeatReadingsUseCase(teplomerId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                        heatReadings = result.data ?: emptyList(),
                        isReadingsLoading = false,
                    )
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isReadingsLoading = true
                    )
                }

                is Resource.Loading -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isReadingsLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun getLastHeatReading(uid: String, teplomerId: Int) {
        this.getLastHeatReadingUseCase(teplomerId, uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                        lastHeatReading = result.data ?: HeatReadingEntity()
                    )
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._heatMeterState.value = heatMeterState.value.copy(
                    )
                }

                is Resource.Loading -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun addWaterReading(
        uid: String,
        newValue: Int,
        currentValue: Int,
        vodomerId: Int
    ) {
        this.addWaterReadingUseCase(
            AddWaterReadingParams(
                uid = uid,
                newValue = newValue,
                currentValue = currentValue,
                meterId = vodomerId
            )
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    SnackbarManager.showMessage("Показання додани")
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                    getLastWaterReading(uid, vodomerId)
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = true

                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun deleteLastWaterReading(
        vodomerId: Int,
        readingId: Int,
        uid: String
    ) {
        this.deleteLastWaterReadingUseCse(
            readingId = readingId,
            uid = uid,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    SnackbarManager.showMessage("Показання видалені")
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                    getLastWaterReading(uid, vodomerId)
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._waterMeterState.value = waterMeterState.value.copy(
                        isLastReadingLoading = true

                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }
    fun deleteLastHeatReading(
        teplomerId: Int,
        readingId: Int,
        uid: String
    ) {
        this.deleteLastHeatReadingUseCase(
            readingId = readingId,
            uid = uid,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    SnackbarManager.showMessage("Показання видалені")
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                    getLastHeatReading(uid, teplomerId)
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = true

                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }
    fun addHeatReading(
        uid: String,
        newValue: Double,
        currentValue: Double,
        teplomerId: Int
    ) {
        this.addHeatReadingUseCase(
            AddHeatReadingParams(
                uid = uid,
                newValue = newValue,
                currentValue = currentValue,
                meterId = teplomerId
            )
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    SnackbarManager.showMessage("Показання додани")
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                    getLastHeatReading( uid,teplomerId)
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = false
                    )
                }

                is Resource.Loading -> {
                    this._heatMeterState.value = heatMeterState.value.copy(
                        isLastReadingLoading = true

                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }
    fun onNewWaterReadingChange(newValue: String) {
        _waterMeterState.value = _waterMeterState.value.copy(
            newWaterReading = newValue
        )
    }

    fun onNewHeatReadingChange(newValue: String) {
        _heatMeterState.value = heatMeterState.value.copy(
            newHeatReading = newValue
        )
    }

    fun setContentDetail(contentDetail: ContentDetail) {
        _contentDetail.value = contentDetail
    }
}