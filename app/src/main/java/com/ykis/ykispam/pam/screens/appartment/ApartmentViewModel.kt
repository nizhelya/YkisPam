/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.cache.payment.PaymentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import com.ykis.ykispam.pam.domain.payment.PaymentItemEntity
import com.ykis.ykispam.pam.domain.payment.request.GetFlatPayment
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.service.request.getTotalDebtService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentsUseCase: GetApartments,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val addFlatByUser: AddFlatByUser,
    private val updateBtiUseCase: UpdateBti,
    private val getTotalDebtServiceUseCase : getTotalDebtService,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {


    var secretKeyUiState by mutableStateOf(SecretKeyUiState())
        private set
    private val secretCode
        get() = secretKeyUiState.secretCode

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email


    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _apartments = MutableLiveData<List<ApartmentEntity>>()

    private val _address = MutableLiveData<List<AddressEntity>>()
    val address: LiveData<List<AddressEntity>> get() = _address
    private val _totalDebt = MutableLiveData<ServiceEntity?>()
    val totalDebt : LiveData<ServiceEntity?> get() = _totalDebt

    private val _totalPay = MutableLiveData<Double>(0.0)
    val totalPay : LiveData<Double> get() = _totalPay

    var currentService :Byte = 0
    var currentServiceTitle :String = ""


    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType


    fun initialize(addressId: Int) {
        observeApartments(addressId)
    }

    private fun observeApartments(addressId: Int) {
        launchCatching {
            if (addressId == 0) {
                _uiState.value = _uiState
                    .value.copy(
                        uid = uid,
                        displayName = displayName,
                        email = email,
                    )
//                if (isConnected && networkType == 2) {
                    getApartmentsByUser(true)
//                } else {
//                    SnackbarManager.showMessage(R.string.error_server_appartment)
//                    getApartmentsByUser(false)
//                }
            } else {
                getFlatFromCache(addressId)
            }
        }
    }
    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
            )
    }
    fun getApartmentsByUser(needFetch: Boolean = true) {
        getApartmentsUseCase(needFetch) { it ->

            if (it.isRight) {
                it.either(::handleFailure) {
                    handleApartments(it)
                }

            }
        }
    }
    fun getFlatFromCache(addressId: Int) {

        _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
        _uiState.value = _uiState.value.copy(
            apartment= _apartment.value!!,
            addressId = _apartment.value!!.addressId,
            address = _apartment.value!!.address,
            houseId =  _apartment.value!!.houseId,
            osmdId =  _apartment.value!!.osmdId,
            osbb = _apartment.value!!.osbb,
            selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value!!.addressId}}"
        )
    }
    private fun handleApartments(apartments: List<ApartmentEntity>) {
        _apartments.value = apartments
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            apartments = apartments,
            apartment = apartments.first(),
            selectedDestination = if (apartments.isEmpty()) {
                "$APARTMENT_SCREEN?$ADDRESS_ID={0}"
            } else {
                "$APARTMENT_SCREEN?$ADDRESS_ID={${apartments.first().addressId}}"
            },
            addressId = if (apartments.isEmpty()) {
                0
            } else {
                apartments.first().addressId
            },
            address = if (apartments.isEmpty()) {
                ""
            } else {
                apartments.first().address
            },
            houseId = if (apartments.isEmpty()) {
                0
            } else {
                apartments.first().houseId
                   },
            osmdId = if (apartments.isEmpty()) {
                0
            } else {
                apartments.first().osmdId
            },
            osbb = if (apartments.isEmpty()) {
                ""
            } else {
                apartments.first().osbb
            },
        )
    }
    fun onSecretCodeChange(newValue: String) {
        secretKeyUiState = secretKeyUiState.copy(secretCode = newValue)
    }
    fun onAddAppartmentClick(restartApp: (String) -> Unit) {
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }
        launchCatching {

            addFlatByUser(secretCode) { it ->
                it.either(::handleFailure) {
                    handleResultText(
                        it, _resultText
                    )
                }
                if (resultText.value?.success == 1) {
                    _uiState.value = resultText.value?.addressId?.let {
                        _uiState.value.copy(
                            addressId = it

                        )
                    }!!
                    SnackbarManager.showMessage(R.string.success_add_flat)
                }

            }
            restartApp(APARTMENT_SCREEN)

        }
        }
fun deleteApartment(addressId: Int, restartApp: (String) -> Unit) {
    launchCatching {

        if (addressId != 0) {
            deleteFlatByUser(addressId) { it ->
                it.either(::handleFailure) {
                    handleResultTextDelete(
                        it, _resultText
                    )
                }
            }

        }
        restartApp(APARTMENT_SCREEN)

    }
}
 fun handleResultText(
    response: GetSimpleResponse,
    result: MutableLiveData<GetSimpleResponse>
) {
    result.value = response
    }

    private fun handleResultTextDelete(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.success_delete_flat)

        }

    }
    fun getTotalService(addressId: Int){
        getTotalDebtServiceUseCase(addressId) { it ->
            it.either(::handleFailure) {
                handle(
                    it,_totalDebt
                )
            }
        }
    }
    fun plusTotalPay(double: Double){
        _totalPay.value = _totalPay.value!!.plus(double)
    }
    fun minusTotalPay(double: Double){
        _totalPay.value = _totalPay.value!!.minus(double)
    }
    private fun handle(address: ServiceEntity?, liveData : MutableLiveData<ServiceEntity?> ){
        liveData.value = address
    }
    fun clearTotal(){
        _totalDebt.value = null
    }

    override fun onCleared() {
        super.onCleared()
        getApartmentsUseCase.unsubscribe()
        deleteFlatByUser.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }


}




