package com.ykis.ykispam.pam.screens.profile

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.firebase.service.repo.RevokeAccessResponse
import com.ykis.ykispam.firebase.service.repo.SignOutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val logService: LogService
) : BaseViewModel(logService) {

    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email
    val providerId get() = firebaseService.getProvider(viewModelScope)

    private val _signOutResponse = MutableStateFlow<SignOutResponse>(Response.Success(false))
    val signOutResponse = _signOutResponse.asStateFlow()

    private val _revokeAccessResponse = MutableStateFlow<RevokeAccessResponse>(Response.Success(false))
    val revokeAccessResponse = _revokeAccessResponse.asStateFlow()

    fun signOut() {
        launchCatching{
            _signOutResponse.value = Response.Loading
            _signOutResponse.value = firebaseService.signOut()
        }
    }

    fun revokeAccess() {
        launchCatching {
            _revokeAccessResponse.value = Response.Loading
            if (providerId == "password") {
                _revokeAccessResponse.value = firebaseService.revokeAccessEmail()
            } else if (providerId == "google.com") {
                _revokeAccessResponse.value = firebaseService.revokeAccess()
            }
        }
    }
}