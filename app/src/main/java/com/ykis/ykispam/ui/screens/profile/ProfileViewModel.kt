package com.ykis.ykispam.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.ClearDatabase
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.firebase.service.repo.RevokeAccessResponse
import com.ykis.ykispam.firebase.service.repo.SignOutResponse
import com.ykis.ykispam.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearDatabase: ClearDatabase,
    private val firebaseService: FirebaseService,
    private val logService: LogService
) : BaseViewModel(logService) {

    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email
    val providerId get() = firebaseService.getProvider(viewModelScope)

    private val _signOutResponse = MutableStateFlow<SignOutResponse>(Resource.Success(false))
    val signOutResponse = _signOutResponse.asStateFlow()

    private val _revokeAccessResponse = MutableStateFlow<RevokeAccessResponse>(Resource.Success(false))
    val revokeAccessResponse = _revokeAccessResponse.asStateFlow()

    fun signOut() {
        launchCatching{
            _signOutResponse.value =Resource.Loading()
            _signOutResponse.value = firebaseService.signOut()
        }
        this.clearDatabase().launchIn(this.viewModelScope)
    }

    fun revokeAccess() {
        launchCatching {
            _revokeAccessResponse.value = Resource.Loading()
            if (providerId == "password") {
                _revokeAccessResponse.value = firebaseService.revokeAccessEmail()
            } else if (providerId == "google.com") {
                _revokeAccessResponse.value = firebaseService.revokeAccess()
            }
        }
    }
}