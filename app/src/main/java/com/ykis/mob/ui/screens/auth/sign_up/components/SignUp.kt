package com.ykis.mob.ui.screens.auth.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.mob.core.CenteredProgressIndicator
import com.ykis.mob.core.Resource
import com.ykis.mob.ui.screens.auth.sign_up.SignUpViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit,
    showErrorMessage: (String) -> Unit

) {
    when(val signUpResponse = viewModel.signUpResponse.collectAsState().value) {
        is Resource.Loading -> CenteredProgressIndicator()
        is Resource.Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp == true) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Resource.Error -> signUpResponse.apply {
            LaunchedEffect(message) {
                print(message)
                message?.let { showErrorMessage(it) }
            }
        }
    }
}
