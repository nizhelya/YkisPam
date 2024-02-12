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

package com.ykis.ykispam.ui.screens.appartment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Constants.VERIFY_EMAIL_SCREEN
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.firebase.service.repo.SignOutResponse
import com.ykis.ykispam.ui.navigation.ADDRESS_ID
import com.ykis.ykispam.ui.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.Graph
import com.ykis.ykispam.ui.navigation.LaunchScreen
import com.ykis.ykispam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.NetworkHandler
import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.domain.apartment.request.GetApartments
import com.ykis.ykispam.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentsUseCase: GetApartments,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val addFlatByUser: AddFlatByUser,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {

    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    val uid get() = firebaseService.uid

    private val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email

    private val signOutResponse = MutableStateFlow<SignOutResponse>(Response.Success(false))

    private val _resultResponse = MutableStateFlow<GetSimpleResponse?>(null)


    private val _apartment = MutableStateFlow<ApartmentEntity>(ApartmentEntity())
    val apartment: StateFlow<ApartmentEntity> get() = _apartment.asStateFlow()


    // TODO: delete address
    private val _address = MutableStateFlow<List<AddressEntity>>(emptyList())
    val address: StateFlow<List<AddressEntity>> get() = _address.asStateFlow()


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    private val _secretCode = MutableStateFlow("")
    val secretCode : StateFlow<String> = _secretCode.asStateFlow()

    // LaunchScreen
    private val _showError = MutableStateFlow(false)
    val showError :StateFlow<Boolean> = _showError.asStateFlow()


    fun getAuthState() = firebaseService.getAuthState(viewModelScope)

    fun onAppStart(
        isUserSignedOut: Boolean,
        openAndPopUp: (String, String) -> Unit,
        restartApp: (String) -> Unit,

    ) {
        _showError.value = false
        if (isUserSignedOut) {
            restartApp(Graph.AUTHENTICATION)
        } else {
            if (isEmailVerified) {
                restartApp(Graph.APARTMENT)
            } else {
                openAndPopUp(VERIFY_EMAIL_SCREEN, LaunchScreen.route)
            }
        }
    }



    fun initialize() {
        if (uid.isNotEmpty()) {
            observeApartments()
        }
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedContentDetail = ContentDetail.EMPTY
            )
    }

    private fun observeApartments() {
        launchCatching {
            _uiState.value = _uiState.value.copy(
                uid = uid,
                displayName = displayName,
                email = email,
            )
            if (isConnected && networkType != 0) {
                getApartmentsByUser( true)
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                getApartmentsByUser(false)
            }

        }
    }
    fun setApartment(addressId: Int) {
        if (addressId != 0) {
            launchCatching {
                _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
                _uiState.value = _apartment.value.let {
                    _uiState.value.copy(
                        apartment = _apartment.value,
                        addressId = _apartment.value.addressId,
                        address = _apartment.value.address,
                        houseId = _apartment.value.houseId,
                        osmdId = _apartment.value.osmdId,
                        osbb = _apartment.value.osbb,
                        selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value.addressId}}"
                    )
                }

            }
        }
    }

    private fun getFlatFromCache(addressId: Int) {
        _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
        _uiState.value = _uiState.value.copy(
            apartment = _apartment.value,
            addressId = _apartment.value.addressId,
            address = _apartment.value.address,
            houseId = _apartment.value.houseId,
            osmdId = _apartment.value.osmdId,
            osbb = _apartment.value.osbb,
            selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value.addressId}}"
        )
    }

    fun setSelectedDetail(contentDetail: ContentDetail, contentType: ContentType) {

        _uiState.value = _uiState.value.copy(
            selectedContentDetail = contentDetail,
            isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
        )
    }

    fun onSecretCodeChange(newValue: String) {
        _secretCode.value = newValue
    }

    fun addApartment(restartApp: (Int) -> Unit) {
        if (_secretCode.value.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }
        launchCatching {
                addFlatByUser(secretCode.value) { it ->
                it.either(::handleFailure) {
                    handleResultText(
                        it, _resultResponse
                    )
                }
                if (_resultResponse.value?.success == 1) {
                    _uiState.value = _uiState.value.copy(
                        addressId = _resultResponse.value!!.addressId
                    )
                    getApartmentsByUser(true)
                    SnackbarManager.showMessage(R.string.success_add_flat)
                    // TODO: rename fun restartApp
                    restartApp(uiState.value.addressId)
                    _secretCode.value = ""
                }
            }
        }

    }

    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableStateFlow<GetSimpleResponse?>,
    ) {
        Log.d("result_test", response.success.toString())
        result.value = response
    }

    fun getApartmentsByUser( needFetch: Boolean = true) {
        getApartmentsUseCase(needFetch) { it ->
            if (it.isRight) {
                it.either(::handleFailure) {
                    handleApartments(it, !needFetch)
                }

            }
        }
    }

    private fun handleApartments(apartments: List<ApartmentEntity>, fromCache: Boolean) {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            apartments = apartments
        )
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getApartmentsByUser(true)
        }
        updateProgress(true)
    }

    fun deleteApartment(addressId: Int, restartApp: (String) -> Unit) {
        launchCatching {

            if (addressId != 0) {
                deleteFlatByUser(addressId) { it ->
                    it.either(::handleFailure) {
                        handleResultTextDelete(
                            it, _resultResponse
                        )
                    }
                }

            }
        }
        _uiState.value.apartment = ApartmentEntity()
    }


    private fun handleResultTextDelete(
        response: GetSimpleResponse,
        result: MutableStateFlow<GetSimpleResponse?>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.success_delete_flat)
            observeApartments()
        }
    }

}




