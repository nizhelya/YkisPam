package com.ykis.ykispam.pam.screens.auth.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.pam.screens.auth.sign_up.SignUpViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit,
    showErrorMessage: (String) -> Unit

) {
    when(val signUpResponse = viewModel.signUpResponse.collectAsState().value) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp == true) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                print(e)
                e.message?.let { showErrorMessage(it) }
            }
        }
    }
}
