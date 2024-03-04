package com.ykis.ykispam.ui.screens.auth.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.screens.auth.sign_up.SignUpViewModel

@Composable
fun ReloadUser(
    viewModel: SignUpViewModel,
    navigateToProfileScreen: () -> Unit
) {
    when (val reloadUserResponse = viewModel.reloadUserResponse.collectAsState().value) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded == true) {
                    navigateToProfileScreen()
                }
            }
        }

        is Resource.Error -> reloadUserResponse.apply {
            LaunchedEffect(message) {
                print(message)
            }
        }
    }
}