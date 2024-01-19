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

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.core.snackbar.SnackbarManager.showMessage
import com.ykis.ykispam.core.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.HandleOnce
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


open class BaseViewModel(
    private val logService: LogService,
) : ViewModel() {
    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    val _uiState = MutableStateFlow(BaseUIState(loading = true))
    val uiState: StateFlow<BaseUIState> = _uiState.asStateFlow()

    protected fun handleFailure(failure: Failure) {
        this.failureData.value = HandleOnce(failure)
        updateProgress(false)
        when (failure) {
            is Failure.FailUpdateBti -> showMessage(R.string.error_update)
            is Failure.FailAddReading -> showMessage(R.string.error_add_reading)
            is Failure.FailIncorrectReading -> showMessage(R.string.error_incorrect_reading)
            is Failure.MissingFields -> showMessage(R.string.error_missing_fields)
            is Failure.FailDeleteFlat -> showMessage(R.string.error_delete_flat)
            is Failure.IncorrectCode -> showMessage(R.string.error_incorrect_code)
            is Failure.FlatAlreadyInDataBase -> showMessage(R.string.error_flat_in_db)
            is Failure.NetworkConnectionError -> showMessage(R.string.error_network)
            is Failure.ServerError -> showMessage(R.string.error_server)
            is Failure.CantSendEmailError -> showMessage(R.string.error_cant_send_email)
            else -> {
                showMessage(R.string.error_server)
            }
        }
    }

    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }


    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )

    fun navigateBack(popUpScreen: () -> Unit) {
        launchCatching {
            popUpScreen()
        }
    }
}


