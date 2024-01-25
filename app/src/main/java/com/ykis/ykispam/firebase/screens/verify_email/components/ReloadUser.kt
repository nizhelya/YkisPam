package com.ykis.ykispam.firebase.screens.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.firebase.screens.sign_up.SignUpViewModel

@Composable
fun ReloadUser(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    when (val reloadUserResponse = viewModel.reloadUserResponse.collectAsState().value) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded == true) {
                    navigateToProfileScreen()
                }
            }
        }

        is Response.Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}