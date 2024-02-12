package com.ykis.ykispam.ui.screens.auth.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ykis.ykispam.ui.screens.auth.sign_up.SignUpViewModel

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

