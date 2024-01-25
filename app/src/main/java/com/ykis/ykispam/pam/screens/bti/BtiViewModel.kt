
package com.ykis.ykispam.pam.screens.bti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _contactUiState = MutableStateFlow<ContactUIState>(ContactUIState())
    val contactUIState : StateFlow<ContactUIState> = _contactUiState.asStateFlow()


    private val email
        get() = _contactUiState.value.email
    private val phone
        get() = _contactUiState.value.phone

    fun onEmailChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(phone = newValue)
    }

    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment


//    private val _apartments = MutableLiveData<List<ApartmentEntity>>()
////    val apartments: LiveData<List<ApartmentEntity>> get() = _apartments


    private val _resultText = MutableLiveData<GetSimpleResponse>()
    fun initialize(apartmentEntity: ApartmentEntity) {
        _contactUiState.value = _contactUiState.value.copy(
            addressId = apartmentEntity.addressId,
            address = apartmentEntity.address,
            email = apartmentEntity.email,
            phone = apartmentEntity.phone,
        )
    }

//    fun setSelectedEmail(addressId: Int ) {
//        /**
//         * We only set isDetailOnlyOpen to true when it's only single pane layout
//         */
//        val email = uiState.value.apartment.find { it.addressId == addressId }
//        _uiState.value = _uiState.value.copy(
//            selectedEmail = email,
//            isDetailOnlyOpen = contentType == ReplyContentType.SINGLE_PANE
//        )
//    }

    fun getBtiFromCache(addressId: Int) {
//        viewModelScope.launch {
//            _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
//            contactUiState.value = contactUiState.value.copy(
//                addressId = _apartment.value!!.addressId,
//                address = if (_apartment.value!!.address.isNullOrEmpty()) {
//                    ""
//                } else {
//                    _apartment.value!!.address
//                },
//                email = _apartment.value!!.email,
//                phone = _apartment.value!!.phone,
//            )
//
//        }
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
//        _apartments.value = apartments


    }

    fun onUpdateBti() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            if (isConnected && networkType != 0) {
                updateBtiUseCase(
                    ApartmentEntity(
                        addressId = _contactUiState.value.addressId,
                        address = _contactUiState.value.address,
                        phone = _contactUiState.value.phone,
                        email = _contactUiState.value.email
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
                getBtiFromCache(_contactUiState.value.addressId)
            }

        }
//        openScreen("$BTI_SCREEN?$ADDRESS_ID=${contactUiState.value.addressId},$ADDRESS=${contactUiState.value.address}")

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
            getBtiFromCache(_contactUiState.value.addressId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getApartmentsUseCase.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }


}