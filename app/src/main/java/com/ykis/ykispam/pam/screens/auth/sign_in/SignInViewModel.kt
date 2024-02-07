package com.ykis.ykispam.pam.screens.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.core.Constants.SIGN_UP_SCREEN
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.firebase.service.repo.OneTapSignInResponse
import com.ykis.ykispam.firebase.service.repo.SignInResponse
import com.ykis.ykispam.firebase.service.repo.SignInWithGoogleResponse
import com.ykis.ykispam.navigation.LaunchScreen
import com.ykis.ykispam.pam.screens.auth.sign_in.components.SingInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.ykis.ykispam.R.string as AppText

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    val oneTapClient: SignInClient,
    logService: LogService
) : BaseViewModel(logService) {

    private val email
        get() = singInUiState.email
    private val password
        get() = singInUiState.password

    var singInUiState by mutableStateOf(SingInUiState())
        private set
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(Response.Success(null))
        private set

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))

    fun onEmailChange(newValue: String) {
        singInUiState = singInUiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        singInUiState = singInUiState.copy(password = newValue)
    }


    fun onSignInClick(openScreen: (String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching() {
                firebaseService.firebaseSignInWithEmailAndPassword(email, password)
            openScreen(LaunchScreen.route)
        }

    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            firebaseService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }

    fun oneTapSignIn() {
        launchCatching {
            oneTapSignInResponse = Response.Loading
            oneTapSignInResponse = firebaseService.oneTapSignInWithGoogle()
        }
    }


    fun signInWithGoogle(googleCredential: AuthCredential) {
        launchCatching {
            oneTapSignInResponse = Response.Loading
            signInWithGoogleResponse = firebaseService.firebaseSignInWithGoogle(googleCredential)
        }
    }

    fun onSignUpClick(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(SIGN_UP_SCREEN)
        }
    }

//    fun navigateToApartmentScreen(openScreen: (String) -> Unit) {
//        launchCatching {
//            openScreen(LAUNCH_SCREEN)
//        }
//    }
}
