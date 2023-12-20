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

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.SIGN_IN_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val addFlatByUser: AddFlatByUser,
    private val updateBtiUseCase: UpdateBti,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {


    var secretKeyUiState by mutableStateOf(SecretKeyUiState())
        private set
    private val secretCode
        get() = secretKeyUiState.secretCode

    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email


    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _address = MutableLiveData<List<AddressEntity>>()
    val address: LiveData<List<AddressEntity>> get() = _address

    private val _totalDebt = MutableLiveData<ServiceEntity?>()
    val totalDebt: LiveData<ServiceEntity?> get() = _totalDebt

    private val _totalPay = MutableLiveData<Double>(0.0)
    val totalPay: LiveData<Double> get() = _totalPay

    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType


//
//
//    fun closeDetailScreen() {
//        _uiState.value = _uiState
//            .value.copy(
//                isDetailOnlyOpen = false,
//            )
//    }
//    fun getFlatFromCache(addressId: Int) {
//        launchCatching {
//
//            _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
//            _uiState.value = _uiState.value.copy(
//                apartment = _apartment.value!!,
//                addressId = _apartment.value!!.addressId,
//                address = _apartment.value!!.address,
//                houseId = _apartment.value!!.houseId,
//                osmdId = _apartment.value!!.osmdId,
//                osbb = _apartment.value!!.osbb,
//                selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value!!.addressId}}"
//            )
//        }
//    }
//
//
//
    fun onSecretCodeChange(newValue: String) {
        secretKeyUiState = secretKeyUiState.copy(secretCode = newValue)
    }

    fun onAddApartmentClick(restartApp: (String) -> Unit) {
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
            restartApp(SPLASH_SCREEN)

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

    private fun handleResultText(
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
//    fun addApartment(openScreen: (String) -> Unit) {
//        openScreen(ADD_APARTMENT_SCREEN)
//    }
//    override fun onCleared() {
//        super.onCleared()
//        deleteFlatByUser.unsubscribe()
//        updateBtiUseCase.unsubscribe()
//    }
}




