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

package com.ykis.mob.ui.screens.appartment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.ext.isValidEmail
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.remote.core.NetworkHandler
import com.ykis.mob.domain.UserRole
import com.ykis.mob.domain.apartment.ApartmentEntity
import com.ykis.mob.domain.apartment.request.AddApartment
import com.ykis.mob.domain.apartment.request.DeleteApartment
import com.ykis.mob.domain.apartment.request.GetApartment
import com.ykis.mob.domain.apartment.request.GetApartmentList
import com.ykis.mob.domain.apartment.request.UpdateBti
import com.ykis.mob.firebase.service.repo.FirebaseService
import com.ykis.mob.firebase.service.repo.LogService
import com.ykis.mob.ui.BaseViewModel
import com.ykis.mob.ui.navigation.Graph
import com.ykis.mob.ui.navigation.LaunchScreen
import com.ykis.mob.ui.navigation.VerifyEmailScreen
import com.ykis.mob.ui.screens.bti.ContactUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentList: GetApartmentList,
    private val getApartment : GetApartment,
    private val deleteApartment : DeleteApartment,
    private val addApartment : AddApartment,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
    private val updateBti: UpdateBti
) : BaseViewModel(logService) {

    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    val uid get() = firebaseService.uid

    private val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email



    private val _apartment = MutableStateFlow(ApartmentEntity())
    val apartment: StateFlow<ApartmentEntity> get() = _apartment.asStateFlow()

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
        Log.d("init_values" , "isEmailVerified: $isEmailVerified \n email:$email \n isUserSignOut : $isUserSignedOut" )
        if (isUserSignedOut || !isEmailVerified) {
            restartApp(Graph.AUTHENTICATION)
        } else {
//            if (isEmailVerified) {
                restartApp(Graph.APARTMENT)
//            } else {
//                openAndPopUp(VerifyEmailScreen.route, LaunchScreen.route)
//            }
        }
    }



    fun observeApartments() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("uid_null_test" , "observeAppartments")
            val userRole = firebaseService.getUserRole()
            val newUid = firebaseService.getUid()
            val newEmail = firebaseService.email
            val newDisplayName = firebaseService.displayName
            _uiState.value = _uiState.value.copy(
                uid = newUid,
                displayName = newDisplayName,
                email = newEmail,
                userRole = userRole,
            )
            Log.d("iii_test" , "uid : $newUid")
            Log.d("iii_test" , "email : $newEmail")
            Log.d("iii_test" , "displayName : $newDisplayName")
            getApartmentList()
        }
    }

    fun getUserRole(){
        viewModelScope.launch(Dispatchers.IO) {
            val userRole = firebaseService.getUserRole()
            var osbbRoleId : Int? = null
            if(userRole == UserRole.OsbbUser){
                osbbRoleId = firebaseService.getOsbbRoleId()
            }
            _uiState.value = _uiState.value.copy(
                uid = uid,
                displayName = displayName,
                email = email,
                userRole = userRole,
                osbbRoleId = osbbRoleId
                )
        }
    }
    fun onSecretCodeChange(newValue: String) {
        _secretCode.value = newValue
    }

    fun addApartment(restartApp: () -> Unit) {
        this.addApartment(
            code = secretCode.value, uid = uid , email = email
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        addressId = result.data!!.addressId
                    )
                    getApartmentList{
                        setAddressId(uiState.value.addressId)
                        restartApp()
                    }
                    SnackbarManager.showMessage(R.string.success_add_flat)
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
            address = _uiState.value.address
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
            this.getApartment(addressId = addressId, uid).onEach {
                    result ->
                when(result){
                    is Resource.Success -> {
                        Log.d("debug_test1", "success")
                        this._uiState.value = _uiState.value.copy(
                            apartment = result.data ?: ApartmentEntity(),
                            addressId = result.data!!.addressId,
                            address = result.data.address,
                            houseId = result.data.houseId,
                            osmdId =result.data.osmdId,
                            osbb = result.data.osbb.toString(),
                            apartmentLoading = false,
                        )
                        this._contactUiState.value = _contactUiState.value.copy(
                            addressId = result.data.addressId,
                            email = result.data.email,
                            phone = result.data.phone,
                            address = result.data.address
                        )
                    }
                    is Resource.Error -> {
                        Log.d("debug_test1", "error")
                        this._uiState.value = _uiState.value.copy(
                            error = result.message ?: "Unexpected error!",
                           apartmentLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        Log.d("debug_test1", "loading")
                        this._uiState.value = _uiState.value.copy(
                             apartmentLoading = true
                        )

                    }
                }
            }.launchIn(this.viewModelScope)
    }

    fun getApartmentList(onSuccess: ()->Unit ={}){
        this.getApartmentList(uiState.value.uid.toString()).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    this._uiState.value = _uiState.value.copy(
                        apartments = result.data ?: emptyList(),
                        mainLoading = false
                    )
                    if(uiState.value.apartments.isEmpty()){
                        this._uiState.value = _uiState.value.copy(
                            apartmentLoading = false
                        )
                    }
                    onSuccess()
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

    fun deleteApartment(
    ) {
        this.deleteApartment(
            addressId = uiState.value.addressId,
            uid = uid
        ).onEach {
            result->
            when(result){
                is Resource.Success -> {
                    SnackbarManager.showMessage(R.string.success_delete_flat)
                    getApartmentList(
                        onSuccess = {
                            this._uiState.value = _uiState.value.copy(
                                mainLoading   = false,
                                addressId = 0
                            )
                        }
                    )
                }
                is Resource.Error -> {
                    this._uiState.value = _uiState.value.copy(
                        error = result.message ?: "Unexpected error!",
                        mainLoading = false
                    )
                    SnackbarManager.showMessage(result.resourceMessage)
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        mainLoading = true
                    )
                }
            }
        }.launchIn(this.viewModelScope)
    }

    fun setAddressId(addressId:Int){
        _uiState.value = uiState.value.copy(
            addressId = addressId
        )
        getApartment(addressId)
    }

}