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

package com.ykis.ykispam

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.SIGN_IN_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.navigation.VERIFY_EMAIL_SCREEN
import com.ykis.ykispam.navigation.YkisRoute
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentsUseCase: GetApartments,
    private val networkHandler: NetworkHandler,
    logService: LogService,
) : BaseViewModel(logService) {

    val showError = mutableStateOf(false)
    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email

    private val _apartments = MutableLiveData<List<ApartmentEntity>>()
    val apartments: LiveData<List<ApartmentEntity>> get() = _apartments


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

//    fun initialize(uid: String) {
////        if (uid.isNotEmpty()) {
//            observeApartments()
////        }
//    }
    init {
        if (uid.isNotEmpty()) {
        observeApartments()
        }
    }
    fun getAuthState() = firebaseService.getAuthState(viewModelScope)

    fun onAppStart(isUserSignedOut: Boolean, openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (isUserSignedOut) {
            openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
        } else {
            if (isEmailVerified) {
                openAndPopUp(APARTMENT_SCREEN, SPLASH_SCREEN)
            } else {
                openAndPopUp(VERIFY_EMAIL_SCREEN, SPLASH_SCREEN)

            }
        }
    }

    fun setSelectedAddress(addressId: Int, contentType: ContentType) {
        /**
         * We only set isDetailOnlyOpen to true when it's only single pane layout
         */
        val apartment = uiState.value.apartments.find { it.addressId == addressId }
        _uiState.value = apartment?.let {
            _uiState.value.copy(
                apartment = it,
                addressId = it.addressId,
                address = it.address,
                houseId = it.houseId,
                osmdId = it.osmdId,
                osbb = it.osbb,
                selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
            )
        }!!
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
//                apartment = _uiState.value.apartments.first()
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
            getApartmentsByUser(true)
        } else {
            SnackbarManager.showMessage(R.string.error_server_appartment)
            getApartmentsByUser(false)
        }

    }
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

private fun handleApartments(apartments: List<ApartmentEntity>) {
    _apartments.value = apartments
    _uiState.value = _uiState.value.copy(
        isDetailOnlyOpen = false,
        apartments = apartments,
        apartment = if (apartments.isEmpty()) {
            ApartmentEntity()
        } else {
            apartments.first()
        },
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


}