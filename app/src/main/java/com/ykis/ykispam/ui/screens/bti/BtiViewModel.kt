
package com.ykis.ykispam.ui.screens.bti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.NetworkHandler
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.request.GetApartmentList
import com.ykis.ykispam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseUIState
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
    private val updateBtiUseCase: UpdateBti,
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

    fun getApartmentList(){
//        this.getApartmentListUseCase(uiState.value.uid ?: "").onEach {
//                result->
//            when(result){
//                is Resource.Success -> {
//                    this._uiState.value = BaseUIState(apartments = result.data ?: emptyList() , isLoading = false)
//                }
//                is Resource.Error -> {
//                    this._uiState.value = BaseUIState(error = result.message ?: "Unexpected error!")
//                }
//                is Resource.Loading -> {
//                    this._uiState.value = BaseUIState(isLoading = true)
//                }
//            }
//        }.launchIn(this.viewModelScope)
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
//                getBtiFromCache(_contactUiState.value.addressId)
            }

        }
    }

    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.updated)
            getApartmentList()
        } else {
            SnackbarManager.showMessage(R.string.error_update)
//            getBtiFromCache(_contactUiState.value.addressId)
        }
    }
}