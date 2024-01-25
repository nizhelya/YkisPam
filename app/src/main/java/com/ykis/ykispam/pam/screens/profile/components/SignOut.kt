package com.ykis.ykispam.pam.screens.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.pam.screens.profile.ProfileViewModel

@Composable
fun SignOut(
    viewModel: ProfileViewModel,
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when(val signOutResponse = viewModel.signOutResponse.collectAsState().value) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
    }
}