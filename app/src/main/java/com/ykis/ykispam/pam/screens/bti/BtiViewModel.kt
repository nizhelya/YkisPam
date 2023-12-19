package com.ykis.ykispam.pam.screens.bti

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.ADDRESS
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.BTI_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BtiViewModel @Inject constructor(
    private val getApartmentsUseCase: GetApartments,
    private val updateBtiUseCase: UpdateBti,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    var contactUiState = mutableStateOf(ContactUIState())
        private set
    private val email
        get() = contactUiState.value.email

    fun onEmailChange(newValue: String) {
        contactUiState.value = contactUiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        contactUiState.value = contactUiState.value.copy(phone = newValue)
    }

    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _apartments = MutableLiveData<List<ApartmentEntity>>()
//    val apartments: LiveData<List<ApartmentEntity>> get() = _apartments


    private val _resultText = MutableLiveData<GetSimpleResponse>()


    fun getBtiFromCache(addressId: Int) {
        viewModelScope.launch {
            _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
            contactUiState.value = contactUiState
                .value.copy(
                    addressId = _apartment.value!!.addressId,
                    address = _apartment.value!!.address,
                    email = _apartment.value!!.email,
                    phone = _apartment.value!!.phone,
                )

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


    }

    fun onUpdateBti(openScreen: (String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            if (isConnected && networkType != 0) {
                updateBtiUseCase(
                    ApartmentEntity(
                        addressId = contactUiState.value.addressId,
                        address = contactUiState.value.address,
                        phone = contactUiState.value.phone,
                        email = contactUiState.value.email
                    )
                ) { it ->
                    it.either(::handleFailure) {
                        handleResultText(
                            it, _resultText
                        )
                    }

                }
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                getBtiFromCache(contactUiState.value.addressId)
            }

        }
        openScreen("$BTI_SCREEN?$ADDRESS_ID=${contactUiState.value.addressId},$ADDRESS=${contactUiState.value.address}")

    }

    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.updated)
            getApartmentsByUser(true)
        } else {
            SnackbarManager.showMessage(R.string.error_update)
            getBtiFromCache(contactUiState.value.addressId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getApartmentsUseCase.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }


}
