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

package com.ykis.ykispam.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.snackbar.SnackbarManager.showMessage
import com.ykis.ykispam.core.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.HandleOnce
import com.ykis.ykispam.firebase.service.repo.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


open class BaseViewModel(
    private val logService: LogService,
) : ViewModel() {
    private var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()
    private var progressData: MutableLiveData<Boolean> = MutableLiveData()

    val _uiState = MutableStateFlow(BaseUIState(isLoading = true))
    val uiState: StateFlow<BaseUIState> = _uiState.asStateFlow()




    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    showMessage(throwable.toSnackbarMessage())
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


