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
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.NetworkHandler
import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.request.AddApartment
import com.ykis.ykispam.domain.apartment.request.DeleteApartment
import com.ykis.ykispam.domain.apartment.request.GetApartment
import com.ykis.ykispam.domain.apartment.request.GetApartmentList
import com.ykis.ykispam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.firebase.service.repo.SignOutResponse
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.ui.navigation.ADDRESS_ID
import com.ykis.ykispam.ui.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.Graph
import com.ykis.ykispam.ui.navigation.LaunchScreen
import com.ykis.ykispam.ui.navigation.VerifyEmailScreen
import com.ykis.ykispam.ui.screens.bti.ContactUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentListUseCase: GetApartmentList,
    private val getApartmentUseCase : GetApartment,
//    private val deleteFlatByUser: DeleteFlatByUser,
    private val deleteApartmentUseCase : DeleteApartment,
    private val addApartmentUseCase : AddApartment,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val database : AppDatabase,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
    private val updateBti: UpdateBti
) : BaseViewModel(logService) {

    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    val uid get() = firebaseService.uid

    private val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email

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


    private val _contactUiState = MutableStateFlow(ContactUIState())
    val contactUIState : StateFlow<ContactUIState> = _contactUiState.asStateFlow()

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
                openAndPopUp(VerifyEmailScreen.route, LaunchScreen.route)
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
            _uiState.value = _uiState.value.copy(
                uid = uid,
                displayName = displayName,
                email = email,
            )
        getApartmentList()
    }
    fun setApartment(addressId: Int) {
        if (addressId != 0) {
            try {
                _apartment.value = database.apartmentDao().getFlatById(addressId)
                _uiState.value =   _uiState.value.copy(
                    apartment = _apartment.value,
                    addressId = _apartment.value.addressId,
                    address = _apartment.value.address,
                    houseId = _apartment.value.houseId,
                    osmdId = _apartment.value.osmdId,
                    osbb = _apartment.value.osbb ?: "",
                    selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value.addressId}}"
                )

            }catch (e:Exception){
                SnackbarManager.showMessage(e.message.toString())
            }

        }
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
        this.addApartmentUseCase(
            code = secretCode.value, uid = uid
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        addressId = result.data!!.addressId
                    )
                    getApartmentList()
                    SnackbarManager.showMessage(R.string.success_add_flat)
                    // TODO: rename fun restartApp
                    restartApp(uiState.value.addressId)
                    _secretCode.value = ""
                }

                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                }

                is Resource.Loading -> {}
            }
        }.launchIn(this.viewModelScope)

    }
    fun initialContactState(){
        _contactUiState.value = ContactUIState(
            email = _uiState.value.apartment.email,
            phone = _uiState.value.apartment.phone,
            addressId = _uiState.value.addressId,
            address = _uiState.value.address.toString()
        )
    }

    fun onEmailChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(phone = newValue)
    }

    fun onUpdateBti(uid : String) {
        if (!email.isValidEmail() && email.isNotEmpty()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        this.updateBti(
            ApartmentEntity(
                addressId = _contactUiState.value.addressId,
                address = _contactUiState.value.address,
                phone = _contactUiState.value.phone,
                email = _contactUiState.value.email,
                uid = uid
            )
        ).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    SnackbarManager.showMessage(R.string.updated)
                    getApartment()
                }
                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                }
                is Resource.Loading -> {}
            }
        }.launchIn(this.viewModelScope)
    }

    fun getApartment(addressId: Int = uiState.value.addressId ){
        try {
            this.getApartmentUseCase(addressId = addressId, uid).onEach {
                    result ->
                when(result){
                    is Resource.Success -> {
                        this._uiState.value = _uiState.value.copy(
                            mainLoading = false,
                            apartment = result.data ?: ApartmentEntity(),
                            addressId = result.data!!.addressId,
                            address = result.data.address,
                            houseId = result.data.houseId,
                            osmdId =result.data.osmdId,
                            osbb = result.data.osbb.toString(),
                            selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${result.data.addressId}}"
                        )
                        Log.d("loading_test" , result.data.toString())
                    }
                    is Resource.Error -> {
                        this._uiState.value = _uiState.value.copy(
                            error = result.message ?: "Unexpected error!",
                            mainLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        this._uiState.value = _uiState.value.copy(
                            mainLoading = true
                        )
                    }
                }
            }.launchIn(this.viewModelScope)
        }catch (e:Exception){
            SnackbarManager.showMessage("aaaa")
        }

    }

    fun getApartmentList(){
        this.getApartmentListUseCase(uid).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    this._uiState.value = _uiState.value.copy(
                        apartments = result.data ?: emptyList() ,
                        mainLoading = false
                    )

                    Log.d("loading_test" ,"success")
                }
                is Resource.Error -> {
                    this._uiState.value = _uiState.value.copy(
                        error = result.message ?: "Unexpected error!",
                        mainLoading = false
                    )
                }
                is Resource.Loading -> {
                    this._uiState.value = _uiState.value.copy(mainLoading = true)
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun deleteApartment() {
        this.deleteApartmentUseCase(
            addressId = uiState.value.addressId,
            uid = uid
        ).onEach {
            result->
            when(result){
                is Resource.Success -> {
                    SnackbarManager.showMessage(R.string.success_delete_flat)
                    getApartmentList()
                    this._uiState.value = _uiState.value.copy(
                        mainLoading   = false,
                        apartment = ApartmentEntity()
                    )
                }
                is Resource.Error -> {
                    this._uiState.value = _uiState.value.copy(
                        error = result.message ?: "Unexpected error!",
                        mainLoading = false
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        mainLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)

    }
}




