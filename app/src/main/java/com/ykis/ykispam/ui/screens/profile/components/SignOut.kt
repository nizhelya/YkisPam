package com.ykis.ykispam.ui.screens.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.screens.profile.ProfileViewModel

@Composable
fun SignOut(
    viewModel: ProfileViewModel,
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when(val signOutResponse = viewModel.signOutResponse.collectAsState().value) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }
        is Resource.Error -> LaunchedEffect(Unit) {
            print(signOutResponse.message)
        }
    }
}