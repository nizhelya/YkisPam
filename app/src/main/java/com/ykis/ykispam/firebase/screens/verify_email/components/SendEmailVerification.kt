package com.ykis.ykispam.firebase.screens.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ykis.ykispam.firebase.screens.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
    navigateToLaunchScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val isEmailVerified = viewModel.isEmailVerified

    LaunchedEffect(isEmailVerified) {
        navigateToLaunchScreen()
    }
}

