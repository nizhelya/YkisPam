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

import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.ADD_APPARTMENT_SCREEN
import com.ykis.ykispam.navigation.PROFILE_SCREEN
import com.ykis.ykispam.navigation.SETTINGS_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.appartment.AppartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.appartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.appartment.request.GetAppartments
import com.ykis.ykispam.pam.domain.appartment.request.UpdateBti
import com.ykis.ykispam.pam.screens.add_appartment.SecretKeyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppartmentViewModel @Inject constructor(
    private val getAppartmentsUseCase: GetAppartments,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val addFlatByUser: AddFlatByUser,
    private val updateBtiUseCase: UpdateBti,
    private val appartmentCacheImpl: AppartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    @ApplicationContext private val context: Context,
    private val logService: LogService
) : BaseViewModel(logService) {

    val options = mutableStateOf<List<String>>(listOf())

    var uiState by mutableStateOf(SecretKeyUiState())
        private set
    private val secretCode
        get() = uiState.secretCode

    private val _appartment = MutableLiveData<AppartmentEntity>()
    val appartment: LiveData<AppartmentEntity> get() = _appartment

    private val _appartments = MutableLiveData<List<AppartmentEntity>>()
    val appartments: LiveData<List<AppartmentEntity>> get() = _appartments

    private val _address = MutableLiveData<List<AddressEntity>>()
    val address: LiveData<List<AddressEntity>> get() = _address

    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    var networkInfo = mutableStateOf(true)

    fun initialize() {
        launchCatching {
            updateProgress(true)
            if (isConnected && networkType == 2) {
                networkInfo.value = true
                getAppartmentsByUserfromServer(true)
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                networkInfo.value = false
                getAppartmentsByUserfromCache(false)
            }
        }
    }

    fun getAppartmentsByUserfromServer(needFetch: Boolean = true) {
        updateProgress(true)
        getAppartmentsUseCase(needFetch) { it ->

            if (it.isRight) {
                it.either(::handleFailure) {
                    handleAppartments(it)
                }
            } else {
                getAppartmentsByUserfromCache(false)
                SnackbarManager.showMessage(R.string.error_server_appartment)
            }
        }
    }

    fun getAppartmentsByUserfromCache(needFetch: Boolean = false) {
        getAppartmentsUseCase(needFetch) { it ->
            it.either(::handleFailure) {
                handleAppartments(it)
            }

        }
    }

    fun getFlatFromCache(addressId: Int, openScreen: (String) -> Unit) {
        viewModelScope.launch {
            _appartment.value = appartmentCacheImpl.getAppartmentById(addressId)
            openScreen(PROFILE_SCREEN)

        }
    }

    fun deleteFlat(addressId: Int, popUpScreean: (String) -> Unit) {

        deleteFlatByUser(addressId) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
//        initialize()
        popUpScreean(SPLASH_SCREEN)

    }

    fun updateBti(addressId: Int, phone: String, email: String) {
        updateBtiUseCase(
            AppartmentEntity(
                addressId = addressId,
                phone = phone,
                email = email
            )
        ) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
    }

    private fun handleAppartments(appartments: List<AppartmentEntity>) {
        _appartments.value = appartments
        updateProgress(false)
    }
       fun onAddClick(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(ADD_APPARTMENT_SCREEN)
        }
    }
    fun onSettingsClick(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(SETTINGS_SCREEN)
        }
    }
    fun onExitAppClick(exitApp: () -> Unit) {
        launchCatching {
            exitApp()
        }
    }



    fun onSecretCodeChange(newValue: String) {
        uiState = uiState.copy(secretCode = newValue)
    }

    fun onAddAppartmentClick(popUpScreean: () -> Unit) {
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }
        addFlatByUser(secretCode) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
        popUpScreean()
//        if (resultText.value?.success == 1) {
//            restartApp(APPARTMENT_SCREEN)
//        }

    }


    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
    }

    override fun onCleared() {
        super.onCleared()
        getAppartmentsUseCase.unsubscribe()
        deleteFlatByUser.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }

}
