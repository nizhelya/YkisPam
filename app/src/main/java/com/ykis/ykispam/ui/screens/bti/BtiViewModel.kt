
package com.ykis.ykispam.ui.screens.bti

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.remote.core.NetworkHandler
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.request.GetApartmentList
import com.ykis.ykispam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BtiViewModel @Inject constructor(
    private val getApartmentListUseCase: GetApartmentList,
    private val updateBti: UpdateBti,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    private val _contactUiState = MutableStateFlow(ContactUIState())
    val contactUIState : StateFlow<ContactUIState> = _contactUiState.asStateFlow()


    private val email
        get() = _contactUiState.value.email

    fun onEmailChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        _contactUiState.value = _contactUiState.value.copy(phone = newValue)
    }

    fun initialize(apartmentEntity: ApartmentEntity) {
        _contactUiState.value = _contactUiState.value.copy(
            addressId = apartmentEntity.addressId,
            address = apartmentEntity.address,
            email = apartmentEntity.email,
            phone = apartmentEntity.phone,
        )
    }
    fun onUpdateBti(uid : String) {
        if (!email.isValidEmail()) {
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
                }
                is Resource.Error -> {
                    SnackbarManager.showMessage(result.resourceMessage)
                }
                is Resource.Loading -> {}
            }
        }.launchIn(this.viewModelScope)
    }
}