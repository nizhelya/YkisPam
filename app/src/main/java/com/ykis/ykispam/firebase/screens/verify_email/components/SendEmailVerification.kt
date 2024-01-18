package com.ykis.ykispam.firebase.screens.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.firebase.screens.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
    navigateToLaunchScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val isEmailVerified = viewModel.isEmailVerified

    LaunchedEffect(isEmailVerified) {
        navigateToLaunchScreen()
    }
}

